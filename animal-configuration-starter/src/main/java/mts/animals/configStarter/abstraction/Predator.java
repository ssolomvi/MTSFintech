package mts.animals.configStarter.abstraction;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Predator extends AbstractAnimal {

    protected Predator(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

}
