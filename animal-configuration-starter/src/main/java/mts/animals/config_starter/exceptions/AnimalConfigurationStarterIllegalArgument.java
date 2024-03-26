package mts.animals.config_starter.exceptions;

public class AnimalConfigurationStarterIllegalArgument extends IllegalArgumentException {
    public AnimalConfigurationStarterIllegalArgument() {
        super();
    }

    /**
     * Constructs an {@code AnimalConfigurationStarterIllegalArgument} with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public AnimalConfigurationStarterIllegalArgument(String s) {
        super(s);
    }

}
