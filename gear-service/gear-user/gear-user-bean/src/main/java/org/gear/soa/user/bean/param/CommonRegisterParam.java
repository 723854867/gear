package org.gear.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRegisterParam extends Param {

	private static final long serialVersionUID = 1092655317319015158L;

	@Min(1)
	private Long inviter;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
}
