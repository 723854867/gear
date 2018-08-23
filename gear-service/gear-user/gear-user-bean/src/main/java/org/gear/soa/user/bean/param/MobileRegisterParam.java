package org.gear.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.MobileParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileRegisterParam extends MobileParam {

	private static final long serialVersionUID = -4353814833436569793L;

	@Min(1)
	private Long inviter;
	@NotEmpty
	private String captcha;
	private String password;
}
