package mts.animals.app.exceptions;

public class AppNullPointerException extends NullPointerException {
    // what exactly should be here?

    @java.io.Serial
    private static final long serialVersionUID = 5162710183389028791L;

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

    // 0: no backtrace filled in, no message computed.
    // 1: backtrace filled in, no message computed.
    // 2: message computed
    private transient int extendedMessageState;
    private transient String extendedMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        // If the stack trace is changed the extended NPE algorithm
        // will compute a wrong message. So compute it beforehand.
        if (extendedMessageState == 0) {
            extendedMessageState = 1;
        } else if (extendedMessageState == 1) {
            extendedMessage = getExtendedNPEMessageApp();
            extendedMessageState = 2;
        }
        return super.fillInStackTrace();
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * <p> If a non-null message was supplied in a constructor it is
     * returned. Otherwise, an implementation specific message or
     * {@code null} is returned.
     *
     * @implNote
     * If no explicit message was passed to the constructor, and as
     * long as certain internal information is available, a verbose
     * description of the null reference is returned.
     * The internal information is not available in deserialized
     * NullPointerExceptions.
     *
     * @return the detail message string, which may be {@code null}.
     */
    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            synchronized(this) {
                if (extendedMessageState == 1) {
                    // Only the original stack trace was filled in. Message will
                    // compute correctly.
                    extendedMessage = getExtendedNPEMessageApp();
                    extendedMessageState = 2;
                }
                return extendedMessage;
            }
        }
        return message;
    }

    /**
     * Get an extended exception message. This returns a string describing
     * the location and cause of the exception. It returns null for
     * exceptions where this is not applicable.
     */
    private native String getExtendedNPEMessageApp();
}
