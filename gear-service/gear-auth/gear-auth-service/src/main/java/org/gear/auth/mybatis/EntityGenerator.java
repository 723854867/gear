package org.gear.auth.mybatis;

import org.gear.auth.bean.Api;
import org.gear.auth.bean.Modular;
import org.gear.auth.bean.ModularApi;
import org.gear.auth.bean.Role;
import org.gear.auth.bean.RoleModular;
import org.gear.auth.bean.UserRole;
import org.gear.auth.param.ApiCreateParam;
import org.gear.auth.param.AuthParam;
import org.gear.auth.param.ModularCreateParam;
import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.param.NameParam;
import org.jupiter.util.KeyUtil;
import org.jupiter.util.lang.DateUtil;

public class EntityGenerator {
	
	public static final Api newApi(ApiCreateParam param) {
		Api instance = new Api();
		instance.setLock(param.isLock());
		instance.setPath(param.getPath());
		instance.setDesc(param.getDesc());
		instance.setLock(param.isLogin());
		instance.setLogged(param.isLogged());
		instance.setIpFilter(param.isIpFilter());
		instance.setCheckAuth(param.isCheckAuth());
		instance.setLockExpire(param.getLockExpire());
		instance.setLockTimeout(param.getLockTimeout());
		instance.setSecurityLevel(param.getSecurityLevel());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}

	public static final Modular newModular(Modular parent, int left, ModularCreateParam param) {
		Modular instance = new Modular();
		instance.setName(param.getName());
		instance.setTrunk(null == parent ? KeyUtil.timebasedId() : parent.getTrunk());
		instance.setLeft(left);
		instance.setRight(instance.getLeft() + 1);
		instance.setLayer(null == parent ? 1 : parent.getLayer() + 1);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final ModularApi newModularApi(AuthParam param) {
		ModularApi instance = new ModularApi();
		instance.setApiId(param.getTarId());
		instance.setModularId((int) param.getId());
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final Role newRole(NameParam param) {
		Role instance = new Role();
		instance.setName(param.getName());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final RoleModular newRoleModular(Role role, Modular modular) {
		RoleModular instance = new RoleModular();
		instance.setRoleId(role.getId());
		instance.setModularId(modular.getId());
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final UserRole newUserRole(UserInfo user, Role role) {
		UserRole instance = new UserRole();
		instance.setUid(user.getUid());
		instance.setRoleId(role.getId());
		instance.setCreated(DateUtil.current());
		return instance;
	}
}
