package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RebindMobileParam extends BindMobileParam {

	private static final long serialVersionUID = 7086359580931892240L;

	@NotEmpty
	private String ocaptcha;
}
