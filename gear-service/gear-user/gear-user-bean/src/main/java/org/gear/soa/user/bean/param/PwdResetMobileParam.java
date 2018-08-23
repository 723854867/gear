package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.MobileParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdResetMobileParam extends MobileParam {

	private static final long serialVersionUID = -4455363021203783714L;

	@NotEmpty
	private String pwd;
	@NotEmpty
	private String captcha;
}
