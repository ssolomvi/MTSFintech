package org.example.service;

import org.example.domain.abstraction.Animal;
import org.example.factory.AnimalSimpleFactory;

public interface CreateAnimalService {
    /**Creates an array of size 10 of randomly generated animals with use of AnimalSimpleFactory */
    default Animal[] createRandomAnimals() {
        int maxCount = 10;
        Animal[] animals = new Animal[maxCount];

        System.out.println("It's default method in interface CreateAnimalService");

        int count = 0;
        Animal animal;
        while (count != maxCount) {
            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals[count++] = animal;
        }

        return animals;
    }

}
