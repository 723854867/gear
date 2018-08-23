package org.gear.user;

import java.text.MessageFormat;

public class KeyGenerator {

	private static final String USER				= "hash:key:user:{0}";
	
	public static final String userKey(long uid) {
		return MessageFormat.format(USER, String.valueOf(uid));
	}
}
