package org.gear.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.EmailParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRegisterParam extends EmailParam {

	private static final long serialVersionUID = 7280568441898653745L;

	@Min(1)
	private Long inviter;
	@NotEmpty
	private String captcha;
	private String password;
}
