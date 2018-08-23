package org.gear.user.manager;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.gear.common.Assert;
import org.gear.common.bean.exception.CodeException;
import org.gear.common.bean.model.Code;
import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.api.UserService;
import org.gear.soa.user.bean.UserCode;
import org.gear.soa.user.bean.entity.UserDevice;
import org.gear.soa.user.bean.entity.Username;
import org.gear.user.ThreadSafeInvoker;
import org.gear.user.event.UserEvents;
import org.gear.user.event.message.LoginMessage;
import org.gear.user.mybatis.EntityGenerator;
import org.gear.user.mybatis.dao.UserDeviceDao;
import org.gear.user.mybatis.dao.UsernameDao;
import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;
import org.jupiter.bean.enums.UsernameType;
import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.jupiter.util.lang.DateUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UsernameManager {
	
	@Resource
	private UsernameDao usernameDao;
	@Resource
	private UserManager userManager;
	@Resource
	private UserService userService;
	@Resource
	private UserDeviceDao userDeviceDao;
	@Resource
	private ThreadSafeInvoker threadSafeInvoker;
	
	/**
	 * 快捷登录(针对邮箱和手机，不需要密码需要验证码。如果账号不存在则直接注册)
	 */
	@Transactional
	public UserInfo login(UsernameType type, String username, OsType os, ClientType client, DeviceType device) {
		Query query = new Query().and(Condition.eq("username", username), Condition.eq("type", type)).forUpdate();
		Username un = username(query);
		if (null == un) {				// 注册(没有邀请人和密码)
			UserInfo user = userManager.register(null, type, username, null);
			un = EntityGenerator.newUsername(user.getUid(), type, username);
			usernameDao.insert(un);
			UserEvents.register(un, user, null);
			return _login(user, username, os, client, device);
		} else {
			UserInfo user = userService.user(un.getUid());
			return threadSafeInvoker.userInvoke(user.getUid(), () -> {
				return _login(user, username, os, client, device);
			});
		}
	}
	
	/**
	 * 账号密码登录(需要先完成注册并且设置了密码)
	 */
	@Transactional
	public UserInfo login(UsernameType type, String username, String pwd, OsType os, ClientType client, DeviceType device) {
		Query query = new Query().and(Condition.eq("username", username), Condition.eq("type", type)).forUpdate();
		Username un = Assert.notNull(username(query), UserCode.USERNAME_NOT_EXIST);
		UserInfo user = userService.user(un.getUid());
		Assert.hasText(user.getPwd(), UserCode.PWD_NOT_SET);
		Assert.isTrue(user.getPwd().equalsIgnoreCase(DigestUtils.md5Hex(pwd + "_" + user.getSalt())), UserCode.PWD_ERR);
		return threadSafeInvoker.userInvoke(user.getUid(), () -> {
			return _login(user, username, os, client, device);
		});
	}
	
	private UserInfo _login(UserInfo user, String username, OsType os, ClientType client, DeviceType device) {
		Query query = new Query().and(Condition.eq("uid", user.getUid()), Condition.eq("type", device));
		UserDevice odevice = userDeviceDao.queryUnique(query);
		if (null != odevice)							// 已经有同类型的设备登录了
			userDeviceDao.deleteByKey(odevice.getId());
		UserDevice ud = EntityGenerator.newUserDevice(user.getUid(), username, os, client, device);
		userDeviceDao.insert(ud);
		user.setDevice(EntityGenerator.newDevice(ud));
		UserEvents.login(new LoginMessage(user, null == odevice ? null : EntityGenerator.newDevice(odevice)));
		return user;
	}
	
	@Transactional
	public UserInfo register(UserInfo inviter, UsernameType type, String username, String password) { 
		UserInfo user = userManager.register(inviter, type, username, password);
		Username un = EntityGenerator.newUsername(user.getUid(), type, username);
		try {
			usernameDao.insert(un);
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.USERNAME_EXIST);
		}
		UserEvents.register(un, user, inviter);
		return user;
	}
	
	@Transactional
	public void bindUsername(UserInfo user, UsernameType type, String username) { 
		Query query = new Query().and(Condition.eq("uid", user.getUid()), Condition.eq("type", type));
		Assert.isNull(usernameDao.queryUnique(query), UserCode.USERNAME_EXIST);
		try {
			usernameDao.insert(EntityGenerator.newUsername(user.getUid(), type, username));
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.USERNAME_EXIST);
		}
	}
	
	/**
	 * 删除原先账号的登录信息
	 */
	@Transactional
	public void rebindUsername(Username username, String nusername) { 
		Query query = new Query().and(Condition.eq("uid", username.getUid()), Condition.eq("type", username.getType())).forUpdate();
		Username un = Assert.notNull(usernameDao.queryUnique(query), Code.FORBID);
		Assert.isTrue(un.getUsername().equals(username.getUsername()), Code.FORBID);
		un.setUsername(nusername);
		un.setUpdated(DateUtil.current());
		try {
			usernameDao.update(un);
			query = new Query().and(Condition.eq("type", username.getType()), Condition.eq("username", username.getUsername()));
			userDeviceDao.deleteByQuery(query);
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.USERNAME_EXIST);
		}
	}
	
	public Username username(Query query) {
		return usernameDao.queryUnique(query);
	}
	
	public Username username(long uid, UsernameType type) {
		return usernameDao.queryUnique(new Query().and(Condition.eq("uid", uid), Condition.eq("type", type)));
	}
	
	public Username username(String username, UsernameType type) {
		return usernameDao.queryUnique(new Query().and(Condition.eq("username", username), Condition.eq("type", type)));
	}
}
