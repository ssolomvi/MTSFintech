package org.example.domain;

import org.example.domain.abstraction.Pet;

import java.math.BigDecimal;

public class Cat extends Pet {

    public Cat(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

}
