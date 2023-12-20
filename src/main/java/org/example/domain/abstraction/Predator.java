package org.example.domain.abstraction;

import java.math.BigDecimal;

public abstract class Predator extends AbstractAnimal {

    protected Predator(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

}
