package mts.animals.app.exceptions;

public class AppNullPointerException extends NullPointerException {
    /**
     * Constructs a {@code AppNullPointerException} with no detail message.
     */
    public AppNullPointerException() {
        super();
    }

    /**
     * Constructs a {@code AppNullPointerException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public AppNullPointerException(String s) {
        super(s);
    }

}
