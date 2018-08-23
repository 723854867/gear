package org.gear.user.mybatis;

import org.apache.commons.codec.digest.DigestUtils;
import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.model.UserInfo.Device;
import org.gear.soa.user.bean.entity.User;
import org.gear.soa.user.bean.entity.UserDevice;
import org.gear.soa.user.bean.entity.UserInviter;
import org.gear.soa.user.bean.entity.Username;
import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;
import org.jupiter.bean.enums.UsernameType;
import org.jupiter.util.KeyUtil;
import org.jupiter.util.lang.DateUtil;
import org.jupiter.util.lang.StringUtil;

public class EntityGenerator {

	public static final Username newUsername(long uid, UsernameType type, String username) {
		Username instance = new Username();
		instance.setUid(uid);
		instance.setType(type);
		instance.setUsername(username);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final User newUser(String password) {
		User instance = new User();
		instance.setName(StringUtil.EMPTY);
		instance.setSalt(KeyUtil.randomCode(6, false));
		instance.setPwd(StringUtil.hasText(password) ? DigestUtils.md5Hex(password + "_" + instance.getSalt()) : StringUtil.EMPTY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final UserInviter newUserInviter(long inviter, long invitee) {
		UserInviter instance = new UserInviter();
		instance.setInviter(inviter);
		instance.setInvitee(invitee);
		instance.setCreated(DateUtil.current());
		instance.setKey(_inviteKey(inviter, invitee));
		return instance;
	}
	
	public static final UserDevice newUserDevice(long uid, String username, OsType os, ClientType client, DeviceType device) {
		UserDevice instance = new UserDevice();
		instance.setOs(os);
		instance.setUid(uid);
		instance.setType(device);
		instance.setClient(client);
		instance.setUsername(username);
		instance.setId(KeyUtil.uuid());
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	private static final String _inviteKey(long inviter, long invitee) {
		return inviter >= invitee ? invitee + "_" + inviter : inviter + "_" + invitee;
	}
	
	public static Device newDevice(UserDevice device) { 
		Device ud = new Device();
		ud.setId(device.getId());
		ud.setOs(device.getOs());
		ud.setType(device.getType());
		ud.setClient(device.getClient());
		ud.setCreated(device.getCreated());
		ud.setUsername(device.getUsername());
		return ud;
	}
	
	public static UserInfo newUser(User user) { 
		UserInfo info = new UserInfo();
		info.setPwd(user.getPwd());
		info.setUid(user.getId());
		info.setName(user.getName());
		info.setSalt(user.getSalt());
		info.setUpdated(user.getUpdated());
		return info;
	}
}
