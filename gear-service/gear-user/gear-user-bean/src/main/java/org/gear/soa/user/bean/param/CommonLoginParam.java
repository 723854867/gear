package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonLoginParam extends LoginParam {

	private static final long serialVersionUID = -7971169458248433539L;

	@NotEmpty
	private String username;
}
