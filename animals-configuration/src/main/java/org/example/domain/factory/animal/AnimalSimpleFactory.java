package org.example.domain.factory.animal;

import org.example.domain.*;
import org.example.domain.abstraction.Animal;
import org.example.domain.enums.AnimalType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.RoundingMode.HALF_EVEN;

//'SimpleFactory'
public final class AnimalSimpleFactory {
    @Value("${animal.cat}")
    private static List<String> catNames;

    @Value("${animal.dog}")
    private static List<String> dogNames;

    @Value("${animal.shark}")
    private static List<String> sharkNames;

    @Value("${animal.tiger}")
    private static List<String> tigerNames;

//    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private AnimalSimpleFactory(AnimalsProperties animalsProperties) {
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

        int randCoefficient = ThreadLocalRandom.current().nextInt(0, 100_000);

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

    private static int generateRandomNameIndex(int namesArraySize) {
        return ThreadLocalRandom.current().nextInt(0, namesArraySize - 1);
//        return ThreadLocalRandom.current().nextInt(0, names.size() - 1);
    }

    private static LocalDate generateRandomBirthDate() {
        long minDay = LocalDate.of(2010, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public static Animal createRandomShark(int randCoefficient) {
        var sharkCost = BigDecimal.valueOf((randCoefficient + 15.78) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Shark("shark", sharkNames.get(generateRandomNameIndex(sharkNames.size())), sharkCost,
                "calm", birthDate);
//        return new Shark("shark", sharkNames.get(generateRandomNameIndex(sharkNames)), sharkCost,
//                "calm", birthDate);
//        return new Shark("shark", String.format("Sharky (%1$td-%1$tm-%1$tY)", birthDate), sharkCost, "calm", birthDate);
    }

    public static Animal createRandomTiger(int randCoefficient) {
        var tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Tiger("tiger", tigerNames.get(generateRandomNameIndex(tigerNames.size())), tigerCost,
                "furious", birthDate);
//        return new Tiger("tiger", tigerNames.get(generateRandomNameIndex(tigerNames)), tigerCost,
//                "furious", birthDate);
    }

    public static Animal createRandomDog(int randCoefficient) {
        var dogCost = BigDecimal.valueOf((randCoefficient + 7.89) * 520).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Dog("dog", dogNames.get(generateRandomNameIndex(dogNames.size())), dogCost,
                "active", birthDate);
//        return new Dog("dog", dogNames.get(generateRandomNameIndex(dogNames)), dogCost,
//                "active", birthDate);
    }

    public static Animal createRandomCat(int randCoefficient) {
        var catCost = BigDecimal.valueOf((randCoefficient + 6.13) * 450).setScale(2, HALF_EVEN);
        var birthDate = generateRandomBirthDate();

        return new Cat("cat", catNames.get(generateRandomNameIndex(catNames.size())), catCost,
                "lazy", birthDate);
//        return new Cat("cat", catNames.get(generateRandomNameIndex(catNames)), catCost,
//                "lazy", birthDate);
    }

}
