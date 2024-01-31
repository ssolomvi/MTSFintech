package org.example.factory.animal;

import org.example.domain.abstraction.Animal;
import org.example.domain.enums.AnimalType;
import org.example.factory.Factory;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AnimalFactory extends Factory {

    Animal createRandomAnimal();

    Animal createAnimal(AnimalType type, String breed, String name,
                        BigDecimal cost, String character, LocalDate birthDate);

}
