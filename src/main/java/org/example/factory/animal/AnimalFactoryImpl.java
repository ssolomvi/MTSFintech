package org.example.factory.animal;

import org.example.domain.Tiger;
import org.example.domain.abstraction.Animal;
import org.example.domain.enums.AnimalType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.math.RoundingMode.HALF_EVEN;

//'Factory'
public class AnimalFactoryImpl implements AnimalFactory {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public Animal createRandomInstance() {
        return createRandomAnimal();
    }

    @Override
    public Animal createRandomAnimal() {
        return AnimalSimpleFactory.createRandomAnimal();
    }

    @Override
    public Animal createAnimal(AnimalType type, String breed, String name,
                               BigDecimal cost, String character, LocalDate birthDate) {

        return AnimalSimpleFactory.createAnimal(type, breed, name, cost, character, birthDate);
    }

    @Override
    public Animal createRandomAnimal(AnimalType type) {
        int randCoefficient = 10;
        var tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, HALF_EVEN);
        var birthDate = LocalDate.of(2010, 1, 1);
        return new Tiger("tiger", "Tigry" + birthDate.format(dateTimeFormatter), tigerCost, "furious", birthDate);
    }
}
