package org.gear.courier.internal;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaConfig implements Serializable {

	private static final long serialVersionUID = -1320659262999178370L;
	
	@SerializedName("captcha_bit_num")
	private int bitNum;
	@SerializedName("captcha_send")
	private boolean send;
	@SerializedName("captcha_interval")
	private int interval;
	@SerializedName("captcha_life_time")
	private int lifeTime;
	@SerializedName("captcha_count_maximum")
	private int countMaximum;
	@SerializedName("captcha_count_life_time")
	private int countLifeTime;
}
