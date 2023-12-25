package org.example.service.impl;

import org.example.domain.abstraction.Animal;
import org.example.factory.animal.AnimalFactory;
import org.example.service.CreateAnimalService;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    private final AnimalFactory animalFactory;

    public CreateAnimalServiceImpl(AnimalFactory animalFactory) {
        if (animalFactory == null) {
            throw new IllegalArgumentException("'animalFactory' is null");
        }

        this.animalFactory = animalFactory;
    }

    /**
     * Creates an array of size 10 of randomly generated animals with use of AnimalSimpleFactory
     */
    @Override
    public Animal[] createRandomAnimals() {
        int maxCount = 10;
        Animal[] animals = new Animal[maxCount];

        System.out.println("It's override method in class CreateAnimalServiceImpl");

        int count = 0;
        Animal animal;
        do {
            animal = animalFactory.createRandomAnimal();
            System.out.println(animal);

            animals[count++] = animal;

        } while (count < maxCount);

        return animals;
    }

    /**
     * Creates an array of size n of randomly generated animals with use of AnimalSimpleFactory
     */
    public Animal[] createRandomAnimals(int n) {
        Animal[] animals = new Animal[n];
        System.out.println("It's overload method in class CreateAnimalServiceImpl");

        Animal animal;
        for (int i = 0; i < n; i++) {
            animal = animalFactory.createRandomAnimal();
            System.out.println(animal);

            animals[i] = animal;
        }

        return animals;
    }

}
