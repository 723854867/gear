package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.MobileParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BindMobileParam extends MobileParam {

	private static final long serialVersionUID = 3701928286428237755L;

	@NotEmpty
	private String captcha;
}
