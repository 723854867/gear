package org.gear.gateway.user.bean.param;

import org.gear.common.bean.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyParam extends Param {

	private static final long serialVersionUID = 2175226337821575255L;

	private String pwd;
	private String name;
}
