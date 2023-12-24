package org.example.domain;

import org.example.domain.abstraction.Pet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Dog extends Pet {

    /** Creates a new object of class Dog */
    public Dog(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
