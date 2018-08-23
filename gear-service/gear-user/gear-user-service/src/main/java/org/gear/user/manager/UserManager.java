package org.gear.user.manager;

import javax.annotation.Resource;

import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.bean.entity.User;
import org.gear.soa.user.bean.entity.UserInviter;
import org.gear.user.mybatis.EntityGenerator;
import org.gear.user.mybatis.dao.UserDao;
import org.gear.user.mybatis.dao.UserInviterDao;
import org.jupiter.bean.enums.UsernameType;
import org.jupiter.util.lang.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class UserManager {
	
	@Resource
	private UserDao userDao;
	@Resource
	private UserInviterDao userInviterDao;
	
	UserInfo register(UserInfo inviter, UsernameType type, String username, String password) { 
		User user = EntityGenerator.newUser(password);
		userDao.insert(user);
		UserInviter ui = null;
		if (null != inviter) {
			ui = EntityGenerator.newUserInviter(inviter.getUid(), user.getId());
			userInviterDao.insert(ui);
		}
		return EntityGenerator.newUser(user);
	}
	
	public void update(UserInfo user) { 
		userDao.updateByInfo(user.getUid(), user.getName(), user.getPwd(), user.getUpdated(), DateUtil.current());
	}
	
	public User user(long uid) {
		return userDao.selectByKey(uid);
	}
}
