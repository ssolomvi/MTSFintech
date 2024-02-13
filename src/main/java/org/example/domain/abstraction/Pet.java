package org.example.domain.abstraction;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Pet extends AbstractAnimal {

    protected Pet(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
