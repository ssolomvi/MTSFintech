package mts.animals.app.exceptions;

public class AppIllegalArgumentException extends IllegalArgumentException {

    /**
     * Constructs an {@code AppIllegalArgumentException} with no
     * detail message.
     */
    public AppIllegalArgumentException() {
        super();
    }

    /**
     * Constructs an {@code AppIllegalArgumentException} with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public AppIllegalArgumentException(String s) {
        super(s);
    }
}
