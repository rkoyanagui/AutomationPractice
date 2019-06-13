package br.com.rkoyanagui.core.exceptions;

public class ParseJsonException extends FrameworkException {

    /**
     * serial version
     */
    private static final long serialVersionUID = 281536643562080339L;

    public ParseJsonException(String message) {
        super(message);
    }

    public ParseJsonException(Throwable cause) {
        super(cause);
    }

    public ParseJsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
