package br.com.rkoyanagui.core.exceptions;

public class FrameworkException extends RuntimeException{

	private static final long serialVersionUID = -2609213458716033666L;

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}
}
