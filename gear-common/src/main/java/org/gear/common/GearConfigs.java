package org.gear.common;

import org.jupiter.bean.model.Option;

@SuppressWarnings("unchecked")
public interface GearConfigs {

	final Option<String, String> CAPTCHA_MSG						= new Option<String, String>("captcha_msg", String.class);
	final Option<String, String> CAPTCHA_MSG_TITLE					= new Option<String, String>("captcha_msg_title", String.class);
	
	final Option<String, ?>[] CAPTCHA_CONFIG_OPTIONS			= new Option[] {
			new Option<String, Integer>("captcha_bit_num", 4),						// 验证码位数
			new Option<String, Boolean>("captcha_send", false),						// 是否发送验证码 
			new Option<String, Integer>("captcha_interval", 60000),					// 验证码获取冷却时间(毫秒)
			new Option<String, Integer>("captcha_life_time", 180000),				// 验证码有效时间(毫秒)
			new Option<String, Integer>("captcha_count_maximum", 10),				// 验证码最大获取次数
			new Option<String, Integer>("captcha_count_life_time", 43200000)};		// 验证码次数重置时长(毫秒)
}
