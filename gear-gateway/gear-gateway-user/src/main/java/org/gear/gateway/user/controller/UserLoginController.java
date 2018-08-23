package org.gear.gateway.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gear.courier.api.CourierService;
import org.gear.soa.user.api.UsernameService;
import org.gear.soa.user.bean.param.CommonLoginParam;
import org.gear.soa.user.bean.param.EmailLoginParam;
import org.gear.soa.user.bean.param.MobileLoginParam;
import org.jupiter.bean.enums.UsernameType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录网关
 * <pre>
 * 快捷登录不需要注册
 * </pre>
 * 
 * @author lynn
 */
@Controller
@RequestMapping("login")
public class UserLoginController {
	
	@Resource
	private CourierService courierService;
	@Resource
	private UsernameService usernameService;
	
	/**
	 * 普通账号密码登录
	 */
	@ResponseBody
	@RequestMapping("common")
	public Object common(@RequestBody @Valid CommonLoginParam param) {
		return usernameService.login(UsernameType.COMMON, param.getUsername(), param.getIdentity(), param.getOs(), param.getClient(), param.getDevice());
	}
	
	/**
	 * 手机密码登录
	 */
	@ResponseBody
	@RequestMapping("mobile")
	public Object mobile(@RequestBody @Valid MobileLoginParam param) {
		return usernameService.login(UsernameType.MOBILE, param.getMobile(), param.getIdentity(), param.getOs(), param.getClient(), param.getDevice());
	}
	
	/**
	 * 邮箱密码登录
	 */
	@ResponseBody
	@RequestMapping("email")
	public Object email(@RequestBody @Valid EmailLoginParam param) {
		return usernameService.login(UsernameType.EMAIL, param.getEmail(), param.getIdentity(), param.getOs(), param.getClient(), param.getDevice());
	}
	
	/**
	 * 手机快捷登录
	 */
	@ResponseBody
	@RequestMapping("mobile/quick")
	public Object mobileQuick(@RequestBody @Valid MobileLoginParam param) {
		courierService.captchaVerify(param.getMobile(), param.getIdentity());
		return usernameService.login(UsernameType.MOBILE, param.getMobile(), param.getOs(), param.getClient(), param.getDevice());
	}
	
	/**
	 * 邮箱快捷登录
	 */
	@ResponseBody
	@RequestMapping("email/quick")
	public Object emailQuick(@RequestBody @Valid EmailLoginParam param) {
		courierService.captchaVerify(param.getEmail(), param.getIdentity());
		return usernameService.login(UsernameType.EMAIL, param.getEmail(), param.getOs(), param.getClient(), param.getDevice());
	}
}
