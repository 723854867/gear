package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.EmailParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BindEmailParam extends EmailParam {

	private static final long serialVersionUID = -1202453974636682251L;

	@NotEmpty
	private String captcha;
}
