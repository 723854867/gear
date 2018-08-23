package org.gear.gateway.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.gear.common.Assert;
import org.gear.common.bean.model.Code;
import org.gear.common.bean.model.Result;
import org.gear.common.bean.model.UserInfo;
import org.gear.courier.api.CourierService;
import org.gear.gateway.user.bean.model.UserTips;
import org.gear.soa.user.api.UserService;
import org.gear.soa.user.api.UsernameService;
import org.gear.soa.user.bean.UserCode;
import org.gear.soa.user.bean.entity.Username;
import org.gear.soa.user.bean.param.BindEmailParam;
import org.gear.soa.user.bean.param.BindMobileParam;
import org.gear.soa.user.bean.param.PwdResetParam;
import org.gear.soa.user.bean.param.PwdResetEmailParam;
import org.gear.soa.user.bean.param.PwdResetMobileParam;
import org.gear.soa.user.bean.param.RebindEmailParam;
import org.gear.soa.user.bean.param.RebindMobileParam;
import org.gear.soa.user.bean.param.UsernameParam;
import org.jupiter.bean.enums.UsernameType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("username")
public class UsernameController {
	
	@Resource
	private UserService userService;
	@Resource
	private CourierService courierService;
	@Resource
	private UsernameService usernameService;

	/**
	 * 根据账号获取用户基本信息
	 */
	@ResponseBody
	@RequestMapping("user/info")
	public Object userTips(@RequestBody @Valid UsernameParam param) { 
		Username username = Assert.notNull(usernameService.username(param.getUser().getUid(), UsernameType.EMAIL), Code.FORBID);
		UserInfo info = userService.user(username.getUid());
		return new UserTips(info);
	}
	
	/**
	 * 绑定邮箱
	 */
	@ResponseBody
	@RequestMapping("bind/email")
	public Object bindEmail(@RequestBody @Valid BindEmailParam param) {
		courierService.captchaVerify(param.getEmail(), param.getCaptcha());
		usernameService.bindEmail(param);
		return Result.ok();
	}
	
	/**
	 * 绑定手机号
	 */
	@ResponseBody
	@RequestMapping("bind/mobile")
	public Object bindMobile(@RequestBody @Valid BindMobileParam param) {
		courierService.captchaVerify(param.getMobile(), param.getCaptcha());
		usernameService.bindMobile(param);
		return Result.ok();
	}
	
	/**
	 * 更换邮箱(用户需要登录)
	 */
	@ResponseBody
	@RequestMapping("rebind/email")
	public Object rebindEmail(@RequestBody @Valid RebindEmailParam param) {
		Username username = Assert.notNull(usernameService.username(param.getUser().getUid(), UsernameType.EMAIL), Code.FORBID);
		courierService.captchaVerify(param.getEmail(), username.getUsername(), param.getCaptcha(), param.getOcaptcha());
		usernameService.rebindUsername(username, param.getEmail());
		return Result.ok();
	}
	
	/**
	 * 更换手机(用户需要登录)
	 */
	@ResponseBody
	@RequestMapping("rebind/mobile")
	public Object rebindMobile(@RequestBody @Valid RebindMobileParam param) {
		Username username = Assert.notNull(usernameService.username(param.getUser().getUid(), UsernameType.MOBILE), Code.FORBID);
		courierService.captchaVerify(param.getMobile(), username.getUsername(), param.getCaptcha(), param.getOcaptcha());
		usernameService.rebindUsername(username, param.getMobile());
		return Result.ok();
	}

	/**
	 * 邮箱设置密码(验证码)
	 */
	@ResponseBody
	@RequestMapping("pwd/set/email")
	public Object pwdResetEmail(@RequestBody @Valid PwdResetEmailParam param) {
		Username username = Assert.notNull(usernameService.username(param.getEmail(), UsernameType.EMAIL), UserCode.USERNAME_NOT_EXIST);
		UserInfo user = userService.user(username.getUid());
		courierService.captchaVerify(param.getEmail(), param.getCaptcha());
		user.setPwd(DigestUtils.md5Hex(param.getPwd() + "_" + user.getSalt()));
		userService.update(user);
		return Result.ok();
	}
	
	/**
	 * 手机设置密码(验证码)
	 */
	@ResponseBody
	@RequestMapping("pwd/set/mobile")
	public Object pwdResetMobile(@RequestBody @Valid PwdResetMobileParam param) {
		Username username = Assert.notNull(usernameService.username(param.getMobile(), UsernameType.MOBILE), UserCode.USERNAME_NOT_EXIST);
		UserInfo user = userService.user(username.getUid());
		courierService.captchaVerify(param.getMobile(), param.getCaptcha());
		user.setPwd(DigestUtils.md5Hex(param.getPwd() + "_" + user.getSalt()));
		userService.update(user);
		return Result.ok();
	}
	
	/**
	 * 账号密码重置密码
	 */
	@ResponseBody
	@RequestMapping("pwd/reset")
	public Object pwdResetCommon(@RequestBody @Valid PwdResetParam param) {
		Username username = Assert.notNull(usernameService.username(param.getUsername(), param.getType()), UserCode.USERNAME_NOT_EXIST);
		UserInfo user = userService.user(username.getUid());
		Assert.hasText(user.getPwd(), UserCode.PWD_NOT_SET);
		Assert.isTrue(user.getPwd().equalsIgnoreCase(param.getOpwd()), UserCode.PWD_ERR);
		user.setPwd(DigestUtils.md5Hex(param.getPwd() + "_" + user.getSalt()));
		userService.update(user);
		return Result.ok();
	}
}
