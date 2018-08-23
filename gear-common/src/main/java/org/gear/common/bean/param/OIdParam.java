package org.gear.common.bean.param;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OIdParam extends Param {

	private static final long serialVersionUID = 8520317784998303603L;

	@Min(1)
	private Integer id;
}
