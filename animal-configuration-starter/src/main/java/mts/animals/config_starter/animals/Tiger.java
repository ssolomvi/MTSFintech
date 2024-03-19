package mts.animals.config_starter.animals;

import mts.animals.config_starter.abstraction.Predator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Tiger extends Predator {

    /**
     * Creates a new object of class Tiger
     */
    public Tiger(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
