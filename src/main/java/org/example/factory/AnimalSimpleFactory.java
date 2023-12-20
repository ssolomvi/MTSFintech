package org.example.factory;

import org.example.domain.Dog;
import org.example.domain.Shark;
import org.example.domain.Tiger;
import org.example.domain.abstraction.Animal;
import org.example.domain.Cat;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.RoundingMode.HALF_EVEN;

public final class AnimalSimpleFactory {

    private AnimalSimpleFactory() {
    }

    public static Animal createRandomAnimal() {
        int rand = ThreadLocalRandom.current().nextInt(1, 4);

        switch (rand) {
            case 1:
                return createShark(rand);

            case 2:
                return createTiger(rand);

            case 3:
                return createDog(rand);

            case 4:
                return createCat(rand);

            default:
                throw new UnsupportedOperationException("Unsupported case");
        }

    }

    public static Animal createShark(int randCoefficient) {
        var sharkCost = BigDecimal.valueOf((randCoefficient + 15.78) * 1000).setScale(2, HALF_EVEN);

        return new Shark("shark", String.format("Sharky_%d", randCoefficient), sharkCost, "calm");
    }

    public static Animal createTiger(int randCoefficient) {
        var tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, HALF_EVEN);

        return new Tiger("tiger", String.format("Tigry_%d", randCoefficient), tigerCost, "furious");
    }

    public static Animal createDog(int randCoefficient) {
        var dogCost = BigDecimal.valueOf((randCoefficient + 7.89) * 520).setScale(2, HALF_EVEN);

        return new Dog("dog", String.format("Doggy_%d", randCoefficient), dogCost, "active");
    }

    public static Animal createCat(int randCoefficient) {
        var catCost = BigDecimal.valueOf((randCoefficient + 6.13) * 450).setScale(2, HALF_EVEN);

        return new Cat("cat", String.format("Kitty-cat_%d", randCoefficient), catCost, "lazy");
    }

}
