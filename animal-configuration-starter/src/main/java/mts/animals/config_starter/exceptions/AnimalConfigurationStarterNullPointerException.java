package mts.animals.config_starter.exceptions;

public class AnimalConfigurationStarterNullPointerException extends NullPointerException {
    @java.io.Serial
    private static final long serialVersionUID = 5162710183389028791L;

    /**
     * Constructs a {@code AnimalConfigurationStarterNullPointerException} with no detail message.
     */
    public AnimalConfigurationStarterNullPointerException() {
        super();
    }

    /**
     * Constructs a {@code AnimalConfigurationStarterNullPointerException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public AnimalConfigurationStarterNullPointerException(String s) {
        super(s);
    }

    // 0: no backtrace filled in, no message computed.
    // 1: backtrace filled in, no message computed.
    // 2: message computed

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    /**
     * {@inheritDoc}
     */
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Get an extended exception message. This returns a string describing
     * the location and cause of the exception. It returns null for
     * exceptions where this is not applicable.
     */
    private native String getExtendedNPEMessageApp();
}
