package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RebindEmailParam extends BindEmailParam {

	private static final long serialVersionUID = -3446095067064066491L;

	@NotEmpty
	private String ocaptcha;
}
