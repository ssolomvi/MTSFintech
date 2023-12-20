package org.example.domain.abstraction;

import java.math.BigDecimal;

public abstract class Pet extends AbstractAnimal {

    protected Pet(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

}
