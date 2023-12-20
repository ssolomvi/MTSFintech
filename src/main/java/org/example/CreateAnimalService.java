package org.example;

import org.example.impl.Cat;
import org.example.impl.Dog;
import org.example.impl.Shark;
import org.example.impl.Tiger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface CreateAnimalService {
    default Animal createShark(int randCoefficient) {
        BigDecimal sharkCost = BigDecimal.valueOf((randCoefficient + 15.78) * 1000).setScale(2, RoundingMode.HALF_EVEN);
        return new Shark("shark", "Sharky" + randCoefficient, sharkCost, "calm");
    }

    default Animal createTiger(int randCoefficient) {
        BigDecimal tigerCost = BigDecimal.valueOf((randCoefficient + 13.56) * 1000).setScale(2, RoundingMode.HALF_EVEN);
        return new Tiger("tiger", "Tigry" + randCoefficient, tigerCost, "furious");
    }

    default Animal createDog(int randCoefficient) {
        BigDecimal dogCost = BigDecimal.valueOf((randCoefficient + 7.89) * 520).setScale(2, RoundingMode.HALF_EVEN);
        return new Dog("dog", "Doggy" + randCoefficient, dogCost, "active");
    }

    default Animal createCat(int randCoefficient) {
        BigDecimal catCost = BigDecimal.valueOf((randCoefficient + 6.13) * 450).setScale(2, RoundingMode.HALF_EVEN);
        return new Cat("cat", "Kitty-cat" + randCoefficient, catCost, "lazy");
    }

    default Animal createUniqueAnimal() {
        int rand = ThreadLocalRandom.current().nextInt(1, 4);
        switch (rand) {
            case 1:
                return createShark(rand);
            case 2:
                return createTiger(rand);
            case 3:
                return createDog(rand);
            default:
                return createCat(rand);
        }
    }

    default List<Animal> createUniqueAnimals() {
        int iter = 0;
        List<Animal> animals = new ArrayList<>();
        System.out.println("It's default method in interface CreateAnimalService");
        while (iter++ != 10) {
            Animal newAnimal = createUniqueAnimal();
            System.out.println(newAnimal);
            animals.add(newAnimal);
        }
        return animals;
    }
}
