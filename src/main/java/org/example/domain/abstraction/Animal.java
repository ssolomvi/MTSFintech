package org.example.domain.abstraction;

import java.math.BigDecimal;

public interface Animal {

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

}
