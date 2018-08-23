package org.gear.soa.user.api;

import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.bean.entity.Username;
import org.gear.soa.user.bean.param.BindEmailParam;
import org.gear.soa.user.bean.param.BindMobileParam;
import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;
import org.jupiter.bean.enums.UsernameType;

public interface UsernameService {
	
	void bindEmail(BindEmailParam param);
	
	void bindMobile(BindMobileParam param);
	
	Username username(long uid, UsernameType type);
	
	Username username(String username, UsernameType type);
	
	void rebindUsername(Username username, String nusername);
	
	UserInfo register(Long inviter, UsernameType type, String username, String password);
	
	UserInfo login(UsernameType type, String username, OsType os, ClientType client, DeviceType device);
	
	UserInfo login(UsernameType type, String username, String pwd, OsType os, ClientType client, DeviceType device);
}
