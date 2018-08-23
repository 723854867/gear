package org.gear.util.gateway.aop;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.gear.auth.AuthCode;
import org.gear.auth.api.AuthService;
import org.gear.auth.bean.Api;
import org.gear.common.Assert;
import org.gear.common.bean.model.Maskable;
import org.gear.common.bean.model.RequestMeta;
import org.gear.common.bean.model.Result;
import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.model.WrapResult;
import org.gear.common.bean.param.Param;
import org.gear.config.api.ConfigService;
import org.gear.config.bean.GlobalConfigs;
import org.gear.log.api.LogService;
import org.gear.log.bean.entity.LogRequest;
import org.gear.soa.user.api.UserService;
import org.gear.soa.user.bean.UserCode;
import org.jupiter.bean.model.MultiListMap;
import org.jupiter.util.KeyUtil;
import org.jupiter.util.lang.DateUtil;
import org.jupiter.util.lang.StringUtil;
import org.jupiter.util.serializer.GsonSerializer;
import org.jupiter.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import com.google.gson.JsonParser;

@Aspect
@Component
public class GatewayInterceptor {
	
	@Autowired(required = false)
	private LogService logService;
	@Autowired(required = false)
	private AuthService authService;
	@Autowired(required = false)
	private UserService userService;
	@Autowired(required = false)
	private GatewayHook gatewayHook;
	@Autowired(required = false)
	private ConfigService configService;
	
	private JsonParser jsonParser = new JsonParser();
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object controllerAround(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest request = WebUtil.request();
		RequestMeta meta = _requestMeta(request, point);
		Api api = null != authService ? authService.api(request.getServletPath()) : null;
		int apiSecurityLevel = null == api ? 1 : api.getSecurityLevel();
		int securityLevel = null == configService ? 1 : configService.config(GlobalConfigs.SERVER_SECURITY_LEVEL);
		Assert.isTrue(apiSecurityLevel >= securityLevel, AuthCode.API_MAINTENANCE);
		String token = request.getHeader("token");
		UserInfo user = (null == userService || !StringUtil.hasText(token)) ? null : (null == api || !api.isLock()) ? userService.getUserByToken(token) : userService.lockUserByToken(token, api.getLockTimeout(), api.getLockExpire());
		Object res = null;
		boolean success = false;
		try {
			meta.setUser(user);
			if (null != api) {
				meta.setApiDesc(api.getDesc());
				if (api.isLock())
					Assert.notNull(user, UserCode.UNLOGIN);
				if (null != authService)
					authService.auth(api, meta);
			}
			if (null != gatewayHook)
				gatewayHook.preProcess(request.getServletPath(), meta, point.getArgs());
			Object result = point.proceed();
			Object response = null;
			if (null != result) {
				if (result instanceof WrapResult)
					response = ((WrapResult) result).getAttach();
				else if (!(result instanceof Result<?>)) {
					if (result instanceof Maskable)
						((Maskable) result).mask();		// 脱敏操作
					response = Result.ok(result);
				} else
					response = result;
			} else
				response = Result.ok();
			success = true;
			res = jsonParser.parse(GsonSerializer.toJson(response));
			return response;
		} catch (Exception e) {
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			res = error.toString();
			throw e;
		} finally {
			if (null != api) {
				if (api.isLogged() && null != logService) 
					logService.logRequest(_requestLog(meta, res, success, point.getArgs()));
				if (api.isLock() && null != user)
					userService.releaseUserLock(user);
			}
		}
	}
	
	private final LogRequest _requestLog(RequestMeta meta, Object response, boolean success, Object[] params) {
		LogRequest log = new LogRequest();
		// 参数处理：所有 param 的子类都设置清除RequestMeta
		List<Object> list = new ArrayList<Object>();
		for(int i = 0 ; i < params.length ; i++) {
			Object param = params[i];
			if (param instanceof InputStream || param instanceof InputStreamSource)
				continue;
			if(param instanceof Serializable) 
				list.add(param);
			if (param instanceof Param)
				((Param) param).setMeta(null);
		}
		Object object = list.size() == 1 ? list.iterator().next() : list.toArray(new Object[] {});
		log.setParam(jsonParser.parse(GsonSerializer.toJson(object)));
		log.setResult(response);
		log.setSuccess(success);
		log.setIp(meta.getIp());
		log.set_id(meta.getId());
		log.setUser(meta.getUser());
		log.setType(meta.getType());
		log.setParam(meta.getPath());
		log.setQuery(meta.getQuery());
		log.setApiDesc(meta.getApiDesc());
		log.setCreated(meta.getTimestamp());
		log.setApiMethod(meta.getApiMethod());
		log.setRtime(DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS));
		log.setCtime(DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS, meta.getTimestamp()));
		return log;
	}
	
	private final RequestMeta _requestMeta(HttpServletRequest request, ProceedingJoinPoint point) throws IOException {
		RequestMeta meta = new RequestMeta();
		meta.setId(KeyUtil.uuid());
		meta.setIp(WebUtil.getIpAddress(request));
		meta.setTimestamp(DateUtil.current());
		meta.setType(request.getMethod());
		meta.setPath(request.getServletPath());
		meta.setQuery(request.getQueryString());
		String method = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
		meta.setApiMethod(method);
		Enumeration<String> enumeration = request.getHeaderNames();
		MultiListMap<String, String> headers = new MultiListMap<String, String>();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			Enumeration<String> temp = request.getHeaders(key);
			while (temp.hasMoreElements()) 
				headers.add(key, temp.nextElement());
		}
		meta.setHeaders(headers);
		
		// 参数处理：所有 param 的子类都设置 requestMeta
		Object[] params = point.getArgs();
		for (Object temp : params) {
			if (temp instanceof Param) {
				Param param = (Param) temp;
				param.setMeta(meta);
				// 参数基本验证
				param.verify();
			}
		}
		return meta;
	}
}
