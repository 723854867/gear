package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.EmailParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdResetEmailParam extends EmailParam {

	private static final long serialVersionUID = -6865934306190556168L;

	@NotEmpty
	private String pwd;
	@NotEmpty
	private String captcha;
}
