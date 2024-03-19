package mts.animals.config_starter.factory.animal;

import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.factory.Factory;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AnimalFactory extends Factory {

    String NAME = "example_AnimalFactory";

    Animal createRandomAnimal();

    Animal createAnimal(AnimalType type, String breed, String name,
                        BigDecimal cost, String character, LocalDate birthDate);

    Animal createRandomAnimal(AnimalType type);

}
