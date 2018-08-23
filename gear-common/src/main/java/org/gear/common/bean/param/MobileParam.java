package org.gear.common.bean.param;

import javax.validation.constraints.NotEmpty;

import org.jupiter.util.PhoneUtil;
import org.jupiter.util.validate.Mobile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileParam extends Param {

	private static final long serialVersionUID = 3967049050957563833L;

	@Mobile
	@NotEmpty
	private String mobile;
	
	@Override
	public void verify() {
		super.verify();
		this.mobile = PhoneUtil.parseMobile(this.mobile);
	}
}
