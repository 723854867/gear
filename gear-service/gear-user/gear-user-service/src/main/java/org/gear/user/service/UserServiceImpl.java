package org.gear.user.service;

import javax.annotation.Resource;

import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.api.UserService;
import org.gear.soa.user.bean.entity.User;
import org.gear.user.manager.UserManager;
import org.gear.user.mybatis.EntityGenerator;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserManager userManager;
	
	@Override
	public UserInfo user(long uid) {
		User user = userManager.user(uid);
		return null == user ? null : EntityGenerator.newUser(user);
	}
	
	@Override
	public void update(UserInfo user) {
		userManager.update(user);
	}

	@Override
	public void releaseUserLock(UserInfo user) {
		
	}

	@Override
	public UserInfo getUserByToken(String token) {
		return null;
	}

	@Override
	public UserInfo lockUserByToken(String token, int timeout, int expire) {
		return null;
	}
}
