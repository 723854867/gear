package org.gear.soa.user.api;

import org.gear.common.bean.model.UserInfo;

public interface UserService {
	
	UserInfo user(long uid);
	
	void update(UserInfo user);
	
	/**
	 * 释放用户锁
	 */
	void releaseUserLock(UserInfo user);

	/**
	 * 通过token获取用户
	 */
	UserInfo getUserByToken(String token);
	
	/**
	 * 通过token获取用户同时获取用户锁
	 * @param timeout 获取用户锁超时时间，超过该时间没获取到用户锁则抛出异常(单位毫秒)
	 * @param expire 用户锁有效时长，超过该时间没有释放锁，则锁自动失效(单位毫秒)
	 * @return
	 */
	UserInfo lockUserByToken(String token, int timeout, int expire);
}
