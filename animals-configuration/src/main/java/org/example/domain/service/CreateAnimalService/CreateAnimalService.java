package org.example.domain.service.CreateAnimalService;

import org.example.domain.abstraction.Animal;
import org.example.domain.enums.AnimalType;
import org.example.domain.factory.animal.AnimalFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface CreateAnimalService {
    String NAME = "example_CreateAnimalService";

    void setAnimalType(AnimalType animalType);

    Animal createAnimal();

    /**
     * Creates an array of size 10 of randomly generated animals with use of AnimalSimpleFactory
     */
    // TODO: how to exclude @Autowired AnimalFactory animalFactory from here?
    default Animal[] createRandomAnimals(@Autowired AnimalFactory animalFactory) {
        int maxCount = 10;
        Animal[] animals = new Animal[maxCount];

        System.out.println("It's default method in interface CreateAnimalService");

        int count = 0;
        Animal animal;
        while (count != maxCount) {
            animal = animalFactory.createRandomAnimal();
//            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals[count++] = animal;
        }

        return animals;
    }

}
