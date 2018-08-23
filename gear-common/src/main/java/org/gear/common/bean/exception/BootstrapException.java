package org.gear.common.bean.exception;

public class BootstrapException extends RuntimeException {

	private static final long serialVersionUID = -3808243767266628077L;

	public BootstrapException() {
		super();
	}
	
	public BootstrapException(String message) {
		super(message);
	}
	
	public BootstrapException(Throwable cause) {
		super(cause);
	}

	public BootstrapException(String message, Throwable cause) {
		super(message, cause);
	}
}
