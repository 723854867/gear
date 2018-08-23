package org.gear.soa.user.bean;

import org.gear.common.bean.model.Code;

public interface UserCode {

	final Code UNLOGIN								= Code.create("code.user.unlogin", "用户未登录");
	final Code PWD_ERR								= Code.create("code.user.pwd.error", "密码错误");
	final Code USER_NOT_EXIST						= Code.create("code.user.not.exist", "用户不存在");
	final Code PWD_NOT_SET							= Code.create("code.user.pwd.not.set", "密码未设置");
	final Code USERNAME_EXIST						= Code.create("code.user.username.exist", "账号已存在");
	final Code INVITER_NOT_EXIST					= Code.create("code.user.inviter.not.exist", "邀请人不存在");
	final Code USERNAME_NOT_EXIST					= Code.create("code.user.username.not.exist", "账号不存在");
}
