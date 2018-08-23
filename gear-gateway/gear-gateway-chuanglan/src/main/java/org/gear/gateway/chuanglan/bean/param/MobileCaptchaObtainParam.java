package org.gear.gateway.chuanglan.bean.param;

import org.gear.common.bean.param.MobileParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileCaptchaObtainParam extends MobileParam {

	private static final long serialVersionUID = -7438322314938591462L;

	// 类型
	private String type;
}
