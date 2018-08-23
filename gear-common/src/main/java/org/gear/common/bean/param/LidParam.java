package org.gear.common.bean.param;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LidParam extends Param {

	private static final long serialVersionUID = 8913574033467423981L;

	@Min(1)
	private long id;
}
