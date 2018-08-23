package org.gear.user.event.message;

import java.io.Serializable;

import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.model.UserInfo.Device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginMessage implements Serializable {

	private static final long serialVersionUID = -5175074116104249027L;
	
	private UserInfo user;
	private Device odevice;
}
