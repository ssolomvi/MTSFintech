package mts.animals.configStarter.factory.animal;

import mts.animals.configStarter.config.AnimalConfigurationProperties;
import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.animals.Cat;
import mts.animals.configStarter.animals.Dog;
import mts.animals.configStarter.animals.Shark;
import mts.animals.configStarter.animals.Tiger;
import mts.animals.configStarter.enums.AnimalType;

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
            throw new IllegalArgumentException("'type' is null");
        }

        int randCoefficient = ThreadLocalRandom.current()
                .nextInt(0, 100_000);

        switch (type) {
            case SHARK:
                return createRandomShark(randCoefficient);

            case TIGER:
                return createRandomTiger(randCoefficient);

            case DOG:
                return createRandomDog(randCoefficient);

            case CAT:
                return createRandomCat(randCoefficient);

            default:
                throw new UnsupportedOperationException("Unsupported case");
        }

    }

    public static Animal createAnimal(AnimalType type, String breed, String name, BigDecimal cost,
                                      String character, LocalDate birthDate) {
        switch (type) {
            case CAT:
                return new Cat(breed, name, cost, character, birthDate);

            case DOG:
                return new Dog(breed, name, cost, character, birthDate);

            case TIGER:
                return new Tiger(breed, name, cost, character, birthDate);

            case SHARK:
                return new Shark(breed, name, cost, character, birthDate);

            default:
                throw new UnsupportedOperationException("Unsupported case");
        }

    }

    private static int getNameIndexFromRandCoefficient(int namesListLength, int randCoefficient) {
        return randCoefficient % namesListLength;
    }

    public static Animal createRandomShark(int randCoefficient) {
        var sharkCost = BigDecimal.valueOf((randCoefficient + 15.78) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Shark("shark",
                animalConfigProperties.getSharkNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getSharkNames().size(), randCoefficient)),
                sharkCost, "calm", birthDate);
    }

    public static Animal createRandomTiger(int randCoefficient) {
        var tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Tiger("tiger",
                animalConfigProperties.getTigerNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getTigerNames().size(), randCoefficient)),
                tigerCost, "furious", birthDate);
    }

    public static Animal createRandomDog(int randCoefficient) {
        var dogCost = BigDecimal.valueOf((randCoefficient + 7.89) * 520).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Dog("dog",
                animalConfigProperties.getDogNames().get(getNameIndexFromRandCoefficient(
                        animalConfigProperties.getDogNames().size(), randCoefficient)),
                dogCost, "active", birthDate);
    }

    public static Animal createRandomCat(int randCoefficient) {
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
