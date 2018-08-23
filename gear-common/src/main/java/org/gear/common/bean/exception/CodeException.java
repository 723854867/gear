package org.gear.common.bean.exception;

import org.gear.common.bean.model.Code;

public class CodeException extends RuntimeException {

	private static final long serialVersionUID = 4570171046746432252L;
	
	private Code code;

	public CodeException() {
		this(Code.SYS_ERR);
	}
	
	public CodeException(Code code) {
		super(code.value());
		this.code = code;
	}
	
	public Code code() {
		return code;
	}
}
