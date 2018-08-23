package org.gear.gateway.user.bean.model;

import java.io.Serializable;

import org.gear.common.bean.model.UserInfo;
import org.jupiter.util.lang.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTips implements Serializable {

	private static final long serialVersionUID = -988770261091169728L;

	private long uid;
	private boolean pwdSet;
	private String nickname;
	
	public UserTips(UserInfo user) {
		this.uid = user.getUid();
		this.nickname = user.getName();
		this.pwdSet = StringUtil.hasText(user.getPwd());
	}
}
