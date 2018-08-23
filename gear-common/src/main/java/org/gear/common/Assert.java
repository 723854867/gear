package org.gear.common;

import org.gear.common.bean.exception.CodeException;
import org.gear.common.bean.model.Code;
import org.jupiter.util.lang.StringUtil;

public class Assert {
	
	public static final void isTrue(Boolean expression) { 
		if (!expression)
			throw new IllegalArgumentException();
	}
	
	public static final void isTrue(Boolean expression, Code code) { 
		if (!expression)
			throw new CodeException(code);
	}
	
	public static final void equal(long src, long tar, Code code) { 
		if (src != tar)
			throw new CodeException(code);
	}
	
	public static final String hasText(String text) { 
		return hasText(text, text + " has no text");
	}

	public static final String hasText(String text, Code code) { 
		if (!StringUtil.hasText(text))
			throw new CodeException(code);
		return text;
	}
	
	public static final String hasText(String text, String message) { 
		if (!StringUtil.hasText(text))
			throw new IllegalArgumentException(message);
		return text;
	}
	
	public static final void isNull(Object value, Code code) { 
		if (null != value)
			throw new CodeException(code);
	}
	
	public static final void isNull(Object value, String message) { 
		if (null != value)
			throw new IllegalArgumentException(message);
	}
	
	public static final <T> T notNull(T value, Code code) {
		if (null == value)
			throw new CodeException(code);
		return value;
	}
}
