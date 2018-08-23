package org.gear.courier.api;

import org.jupiter.bean.model.Pair;

public interface CourierService {
	
	/**
	 * 获取验证码
	 */
	String getCaptcha(String identity);

	/**
	 * 验证码验证
	 */
	void captchaVerify(String... args);
	
	/**
	 * 获取验证码
	 */
	Pair<String, Boolean> captchaObtain(String identity);
}
