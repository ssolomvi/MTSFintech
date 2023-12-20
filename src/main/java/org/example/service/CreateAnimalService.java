package org.example.service;

import org.example.domain.abstraction.Animal;
import org.example.factory.AnimalSimpleFactory;

import java.util.ArrayList;
import java.util.List;

public interface CreateAnimalService {

    default List<Animal> createRandomAnimals() {
        int maxCount = 10;
        List<Animal> animals = new ArrayList<>(maxCount);

        System.out.println("It's default method in interface CreateAnimalService");

        int count = 0;
        Animal animal;
        while (count++ != maxCount) {
            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals.add(animal);
        }

        return animals;
    }

}
