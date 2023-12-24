package org.example.domain;

import org.example.domain.abstraction.Predator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Shark extends Predator {

    /** Creates a new object of class Shark */
    public Shark(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
