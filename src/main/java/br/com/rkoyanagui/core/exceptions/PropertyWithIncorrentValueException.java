package br.com.rkoyanagui.core.exceptions;

public class PropertyWithIncorrentValueException extends FrameworkException {

	private static final long serialVersionUID = 4619155958076758202L;

	public PropertyWithIncorrentValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyWithIncorrentValueException(String message) {
		super(message);
	}

	public PropertyWithIncorrentValueException(Throwable cause) {
		super(cause);
	}
}
