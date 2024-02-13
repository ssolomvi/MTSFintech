package mts.animals.configStarter.factory.animal;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.factory.Factory;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AnimalFactory extends Factory {

    String NAME = "example_AnimalFactory";

    Animal createRandomAnimal();

    Animal createAnimal(AnimalType type, String breed, String name,
                        BigDecimal cost, String character, LocalDate birthDate);

    Animal createRandomAnimal(AnimalType type);

}
