package org.gear.user.service;

import javax.annotation.Resource;

import org.gear.common.Assert;
import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.api.UserService;
import org.gear.soa.user.api.UsernameService;
import org.gear.soa.user.bean.UserCode;
import org.gear.soa.user.bean.entity.Username;
import org.gear.soa.user.bean.param.BindEmailParam;
import org.gear.soa.user.bean.param.BindMobileParam;
import org.gear.user.ThreadSafeInvoker;
import org.gear.user.manager.UsernameManager;
import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;
import org.jupiter.bean.enums.UsernameType;
import org.springframework.stereotype.Service;

@Service("usernameService")
public class UsernameServiceImpl implements UsernameService {
	
	@Resource
	private UserService userService;
	@Resource
	private UsernameManager usernameManager;
	@Resource
	private ThreadSafeInvoker threadSafeInvoker;
	
	@Override
	public void bindEmail(BindEmailParam param) {
		usernameManager.bindUsername(param.getMeta().getUser(), UsernameType.EMAIL, param.getEmail());
	}

	@Override
	public void bindMobile(BindMobileParam param) {
		usernameManager.bindUsername(param.getMeta().getUser(), UsernameType.MOBILE, param.getMobile());
	}
	
	@Override
	public Username username(long uid, UsernameType type) {
		return usernameManager.username(uid, type);
	}
	
	@Override
	public Username username(String username, UsernameType type) {
		return usernameManager.username(username, type);
	}

	@Override
	public void rebindUsername(Username username, String nusername) {
		usernameManager.rebindUsername(username, nusername);
	}

	@Override
	public UserInfo register(Long inviterId, UsernameType type, String username, String password) {
		UserInfo inviter = null;
		if (null != inviterId)
			inviter = Assert.notNull(userService.user(inviterId), UserCode.INVITER_NOT_EXIST);
		return usernameManager.register(inviter, type, username, password);
	}
	
	@Override
	public UserInfo login(UsernameType type, String username, OsType os, ClientType client, DeviceType device) {
		return usernameManager.login(type, username, os, client, device);
	}
	
	@Override
	public UserInfo login(UsernameType type, String username, String pwd, OsType os, ClientType client, DeviceType device) {
		return usernameManager.login(type, username, pwd, os, client, device);
	}
}
