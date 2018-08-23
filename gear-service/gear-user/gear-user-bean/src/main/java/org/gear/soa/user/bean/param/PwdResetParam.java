package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdResetParam extends UsernameParam {

	private static final long serialVersionUID = 5652272234100054500L;

	@NotEmpty
	private String pwd;
	@NotEmpty
	private String opwd;
}
