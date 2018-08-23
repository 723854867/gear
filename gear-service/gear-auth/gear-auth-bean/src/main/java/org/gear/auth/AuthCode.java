package org.gear.auth;

import org.gear.common.bean.model.Code;

public interface AuthCode {

	final Code API_EXIST					= Code.create("code.auth.api.exist", "接口已存在");
	final Code API_NOT_EXIST				= Code.create("code.auth.api.not.exist", "接口不存在");
	final Code ROLE_NOT_EXIST				= Code.create("code.auth.role.not.exist", "角色不存在");
	final Code API_MAINTENANCE				= Code.create("code.auth.api.maintenance", "该服务暂不可用");
	final Code MODULAR_NOT_EXIST			= Code.create("code.auth.modular.not.exist", "模块不存在");
	final Code USER_ROLE_EXIST				= Code.create("code.auth.user.role.exist", "用户已拥有该角色");
}
