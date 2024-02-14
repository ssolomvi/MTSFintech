package mts.animals.configStarter.factory.animal;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;

import java.math.BigDecimal;
import java.time.LocalDate;

//'Factory'
public class AnimalFactoryImpl implements AnimalFactory {

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
        return AnimalSimpleFactory.createAnimal(type);
    }

}
