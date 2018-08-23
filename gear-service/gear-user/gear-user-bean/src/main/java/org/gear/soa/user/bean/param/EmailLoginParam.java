package org.gear.soa.user.bean.param;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailLoginParam extends LoginParam {

	private static final long serialVersionUID = -885298858782311508L;

	@Email
	@NotEmpty
	private String email;
}
