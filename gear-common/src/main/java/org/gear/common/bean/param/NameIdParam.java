package org.gear.common.bean.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameIdParam extends IdParam {

	private static final long serialVersionUID = 7598206259560561592L;

	@NotEmpty
	private String name;
}
