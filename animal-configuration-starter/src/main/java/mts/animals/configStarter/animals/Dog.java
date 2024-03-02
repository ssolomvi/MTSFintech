package mts.animals.configStarter.animals;

import mts.animals.configStarter.abstraction.Pet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Dog extends Pet {

    /**
     * Creates a new object of class Dog
     */
    public Dog(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
