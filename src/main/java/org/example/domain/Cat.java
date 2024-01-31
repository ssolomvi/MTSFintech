package org.example.domain;

import org.example.domain.abstraction.Pet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cat extends Pet {

    /**
     * Creates a new object of class Cat
     */
    public Cat(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
