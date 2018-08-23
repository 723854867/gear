package org.gear.common.bean.param;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailParam extends Param {

	private static final long serialVersionUID = -3120113596996703260L;
	
	@Email
	@NotEmpty
	private String email;
}
