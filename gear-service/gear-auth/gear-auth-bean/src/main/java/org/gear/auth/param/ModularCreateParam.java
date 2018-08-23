package org.gear.auth.param;

import javax.validation.constraints.NotEmpty;

import org.gear.common.bean.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModularCreateParam extends Param {

	private static final long serialVersionUID = -1313600293120991710L;

	@NotEmpty
	private String name;
	private Integer parent;
}
