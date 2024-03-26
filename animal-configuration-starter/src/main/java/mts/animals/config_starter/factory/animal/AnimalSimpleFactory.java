package mts.animals.config_starter.factory.animal;

import mts.animals.config_starter.config.AnimalConfigurationProperties;
import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.animals.Cat;
import mts.animals.config_starter.animals.Dog;
import mts.animals.config_starter.animals.Shark;
import mts.animals.config_starter.animals.Tiger;
import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterIllegalArgument;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.RoundingMode.HALF_EVEN;

//'SimpleFactory'
public final class AnimalSimpleFactory {
    private static AnimalConfigurationProperties animalConfigProperties;

//    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private AnimalSimpleFactory() {
    }

    // public static method is almost always better than using constructor in app's code
    public static Animal createRandomAnimal() {
        int randCoefficient = ThreadLocalRandom.current().nextInt(0, 3);
        var type = AnimalType.values()[randCoefficient];
        ++randCoefficient;

        return createAnimal(type);
    }

    public static Animal createAnimal(AnimalType type) {
        if (type == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalSimpleFactory::createAnimal: 'type' is null");
        }

        int randCoefficient = ThreadLocalRandom.current()
                .nextInt(0, 100_000);

        return switch (type) {
            case SHARK -> createRandomShark(randCoefficient);
            case TIGER -> createRandomTiger(randCoefficient);
            case DOG -> createRandomDog(randCoefficient);
            case CAT -> createRandomCat(randCoefficient);
        };

    }

    public static Animal createAnimal(AnimalType type, String breed, String name, BigDecimal cost,
                                      String character, LocalDate birthDate) {
        if (type == null || breed == null || name == null || cost == null || character == null || birthDate == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalSimpleFactory::createAnimal: params of " +
                    "constructor should not be null");
        }
        return switch (type) {
            case CAT -> new Cat(breed, name, cost, character, birthDate);
            case DOG -> new Dog(breed, name, cost, character, birthDate);
            case TIGER -> new Tiger(breed, name, cost, character, birthDate);
            case SHARK -> new Shark(breed, name, cost, character, birthDate);
        };

    }

    private static int getNameIndexFromRandCoefficient(int namesListLength, int randCoefficient) {
        return randCoefficient % namesListLength;
    }

    public static Animal createRandomShark(int randCoefficient) {
        if (randCoefficient == 0) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalSimpleFactory::createRandomShark: param " +
                    "randCoefficient should not be null");
        }
        var sharkCost = BigDecimal.valueOf((randCoefficient + 15.78) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Shark("shark",
                animalConfigProperties.getSharkNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getSharkNames().size(), randCoefficient)),
                sharkCost, "calm", birthDate);
    }

    public static Animal createRandomTiger(int randCoefficient) {
        if (randCoefficient == 0) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalSimpleFactory::createRandomTiger: param " +
                    "randCoefficient should not be null");
        }
        var tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Tiger("tiger",
                animalConfigProperties.getTigerNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getTigerNames().size(), randCoefficient)),
                tigerCost, "furious", birthDate);
    }

    public static Animal createRandomDog(int randCoefficient) {
        if (randCoefficient == 0) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalSimpleFactory::createRandomDog: param " +
                    "randCoefficient should not be null");
        }
        var dogCost = BigDecimal.valueOf((randCoefficient + 7.89) * 520).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Dog("dog",
                animalConfigProperties.getDogNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getDogNames().size(), randCoefficient)),
                dogCost, "active", birthDate);
    }

    public static Animal createRandomCat(int randCoefficient) {
        if (randCoefficient == 0) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalSimpleFactory::createRandomCat: param " +
                    "randCoefficient should not be null");
        }
        var catCost = BigDecimal.valueOf((randCoefficient + 6.13) * 450).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Cat("cat",
                animalConfigProperties.getCatNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getCatNames().size(), randCoefficient)),
                catCost, "lazy", birthDate);
    }

    private static LocalDate generateRandomBirthDate() {
        long minDay = LocalDate.of(2010, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay);
    }

}
