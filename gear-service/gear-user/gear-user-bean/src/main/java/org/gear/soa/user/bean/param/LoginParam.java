package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gear.common.bean.param.Param;
import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginParam extends Param {

	private static final long serialVersionUID = -562358235293799864L;

	@NotNull
	private OsType os;
	@NotEmpty
	private String identity;
	@NotNull
	private ClientType client;
	@NotNull
	private DeviceType device;
}
