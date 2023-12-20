package org.example.domain;

import org.example.domain.abstraction.Predator;

import java.math.BigDecimal;

public class Shark extends Predator {

    public Shark(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

}
