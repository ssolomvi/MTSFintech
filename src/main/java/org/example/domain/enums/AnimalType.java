package org.example.domain.enums;

public enum AnimalType {

    CAT("PET"),
    DOG("PET"),
    TIGER("PREDATOR"),
    SHARK("PREDATOR");

    private final String type;

    AnimalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
