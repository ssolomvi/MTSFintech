package org.example;

import java.math.BigDecimal;

public interface Animal {
    /** Returns a breed of an animal */
    public String getBreed();

    /** Returns a name of an animal */
    public String getName();

    /** Returns a cost of an animal */
    public BigDecimal getCost();

    /** Returns a character of an animal */
    public String getCharacter();
}
