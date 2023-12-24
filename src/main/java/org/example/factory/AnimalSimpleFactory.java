package org.example.factory;

import org.example.domain.Dog;
import org.example.domain.Shark;
import org.example.domain.Tiger;
import org.example.domain.abstraction.Animal;
import org.example.domain.Cat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.RoundingMode.HALF_EVEN;

public final class AnimalSimpleFactory {

    private AnimalSimpleFactory() {
    }

    public static Animal createRandomAnimal() {
        int randCoefficient = ThreadLocalRandom.current().nextInt(0, 3);
        Animal.AnimalsType type = Animal.AnimalsType.values()[randCoefficient];
        ++randCoefficient;
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

    public static Animal createAnimal(Animal.AnimalsType type, String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static Animal createRandomShark(int randCoefficient) {
        var sharkCost = BigDecimal.valueOf((randCoefficient + 15.78) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();
        return new Shark("shark", "Sharky" + birthDate.format(dateTimeFormatter), sharkCost, "calm", birthDate);
    }

    public static Animal createRandomTiger(int randCoefficient) {
        var tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();
        return new Tiger("tiger", "Tigry" + birthDate.format(dateTimeFormatter), tigerCost, "furious", birthDate);
    }

    public static Animal createRandomDog(int randCoefficient) {
        var dogCost = BigDecimal.valueOf((randCoefficient + 7.89) * 520).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();
        return new Dog("dog", "Doggy" + birthDate.format(dateTimeFormatter), dogCost, "active", birthDate);
    }

    public static Animal createRandomCat(int randCoefficient) {
        var catCost = BigDecimal.valueOf((randCoefficient + 6.13) * 450).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();
        return new Cat("cat", "Kitty" + birthDate.format(dateTimeFormatter), catCost, "lazy", birthDate);
    }

    private static LocalDate generateRandomBirthDate() {
        long minDay = LocalDate.of(2010, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
