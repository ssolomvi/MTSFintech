package mts.animals.config_starter.enums;

import mts.animals.config_starter.exceptions.AnimalConfigurationStarterIllegalArgument;

public enum AnimalType {

    CAT("PET"),
    DOG("PET"),
    TIGER("PREDATOR"),
    SHARK("PREDATOR");

    private final String type;

    AnimalType(String type) {
        if (type == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalType:: param type is null");
        }
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
