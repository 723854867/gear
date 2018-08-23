package org.gear.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.jupiter.util.PhoneUtil;
import org.jupiter.util.validate.Mobile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileLoginParam extends LoginParam {

	private static final long serialVersionUID = -4772510118317611287L;
	
	@Mobile
	@NotEmpty
	private String mobile;
	
	@Override
	public void verify() {
		super.verify();
		this.mobile = PhoneUtil.parseMobile(mobile);
	}
}
