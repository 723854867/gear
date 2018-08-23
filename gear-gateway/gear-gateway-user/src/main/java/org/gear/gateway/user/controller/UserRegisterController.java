package org.gear.gateway.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gear.courier.api.CourierService;
import org.gear.soa.user.api.UsernameService;
import org.gear.soa.user.bean.param.CommonRegisterParam;
import org.gear.soa.user.bean.param.EmailRegisterParam;
import org.gear.soa.user.bean.param.MobileRegisterParam;
import org.jupiter.bean.enums.UsernameType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册网关
 * <pre>
 * 如果是手机和邮箱注册密码可以为空。这时可以使用快捷登录
 * 普通账号必须设置密码
 * </pre>
 * 
 * @author lynn
 */
@Controller
@RequestMapping("register")
public class UserRegisterController {

	@Resource
	private CourierService courierService;
	@Resource
	private UsernameService usernameService;

	@ResponseBody
	@RequestMapping("common")
	public Object common(@RequestBody @Valid CommonRegisterParam param) { 
		return usernameService.register(param.getInviter(), UsernameType.COMMON, param.getUsername(), param.getPassword());
	}
	
	@ResponseBody
	@RequestMapping("mobile")
	public Object mobile(@RequestBody @Valid MobileRegisterParam param) { 
		courierService.captchaVerify(param.getMobile(), param.getCaptcha());
		return usernameService.register(param.getInviter(), UsernameType.MOBILE, param.getMobile(), param.getPassword());
	}
	
	@ResponseBody
	@RequestMapping("email")
	public Object email(@RequestBody @Valid EmailRegisterParam param) {
		courierService.captchaVerify(param.getEmail(), param.getCaptcha());
		return usernameService.register(param.getInviter(), UsernameType.EMAIL, param.getEmail(), param.getPassword());
	}
}
