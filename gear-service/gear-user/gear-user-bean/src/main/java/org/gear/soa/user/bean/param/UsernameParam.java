package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gear.common.bean.param.Param;
import org.jupiter.bean.enums.UsernameType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameParam extends Param {

	private static final long serialVersionUID = 7521889552063795049L;

	@NotEmpty
	private String username;
	@NotNull
	private UsernameType type;
}
