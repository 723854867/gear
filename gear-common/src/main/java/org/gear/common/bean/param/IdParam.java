package org.gear.common.bean.param;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdParam extends Param {

	private static final long serialVersionUID = 7374220101021166165L;

	@Min(1)
	private int id;
}
