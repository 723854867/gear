package org.gear.auth.param;

import javax.validation.constraints.Min;

import org.gear.common.bean.param.LidParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthParam extends LidParam {

	private static final long serialVersionUID = -6433022683710374357L;

	@Min(1)
	private int tarId;
	private boolean unauth;
}
