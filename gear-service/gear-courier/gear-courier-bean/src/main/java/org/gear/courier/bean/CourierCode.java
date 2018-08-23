package org.gear.courier.bean;

import org.gear.common.bean.model.Code;

public interface CourierCode {

	final Code CAPTCHA_ERR						= Code.create("code.courier.captcha.error", "验证码错误");
	final Code CAPTCHA_OBTAIN_FREQ				= Code.create("code.courier.captcha.obtain.freq", "验证码获取太频繁");
	final Code CAPTCHA_OBTAIN_COUNT_LIMIT		= Code.create("code.courier.captcha.obtain.count.limit", "验证码获取次数限制");
}
