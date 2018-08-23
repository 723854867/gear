package org.gear.gateway.courier.bean.param;

import org.gear.common.bean.param.EmailParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCaptchaObtainParam extends EmailParam {

	private static final long serialVersionUID = -7438322314938591462L;

	// 类型
	private String type;
}
