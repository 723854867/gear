package org.gear.soa.user.bean.model;

import java.io.Serializable;

import org.gear.common.bean.model.UserInfo;
import org.gear.soa.user.bean.entity.Username;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMsg implements Serializable {

	private static final long serialVersionUID = 4469634032023531875L;

	private UserInfo user;
	private UserInfo inviter;
	private Username username;
}
