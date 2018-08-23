package org.gear.common.bean.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameParam extends Param {

	private static final long serialVersionUID = 4001999000379667680L;

	@NotEmpty
	private String name;
}
