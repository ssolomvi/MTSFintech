package mts.animals.config_starter.factory.animal;

import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterIllegalArgument;

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
        if (type == null || breed == null || name == null || cost == null || character == null || birthDate == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalFactoryImpl::createAnimal: params of " +
                    "constructor should not be null");
        }
        return AnimalSimpleFactory.createAnimal(type, breed, name, cost, character, birthDate);
    }

    @Override
    public Animal createRandomAnimal(AnimalType type) {
        if (type == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalFactoryImpl::createRandomAnimal: param type " +
                    "should not be null");
        }
        return AnimalSimpleFactory.createAnimal(type);
    }

}
