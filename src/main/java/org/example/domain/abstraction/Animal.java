package org.example.domain.abstraction;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Animal {
    enum AnimalsType {
        CAT, DOG, TIGER, SHARK
    }

    /**
     * Returns a breed of an animal
     */
    String getBreed();

    /**
     * Returns a name of an animal
     */
    String getName();

    /**
     * Returns a cost of an animal
     */
    BigDecimal getCost();

    /**
     * Returns a character of an animal
     */
    String getCharacter();

    /**
     * Returns a birthdate of an animal
     */
    LocalDate getBirthDate();
}
