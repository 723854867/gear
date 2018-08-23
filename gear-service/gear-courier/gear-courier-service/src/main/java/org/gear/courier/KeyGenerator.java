package org.gear.courier;

import java.text.MessageFormat;

public class KeyGenerator {

	private static final String CAPTCHA_KEY				= "string:captcha:key:{0}";
	private static final String CAPTCHA_KEY_COUNT		= "string:captcha:key:count:{0}";
	
	public static final String captchaKey(String identity) { 
		return MessageFormat.format(CAPTCHA_KEY, identity);
	}
	
	public static final String captchaKeyCount(String identity) { 
		return MessageFormat.format(CAPTCHA_KEY_COUNT, identity);
	}
}
