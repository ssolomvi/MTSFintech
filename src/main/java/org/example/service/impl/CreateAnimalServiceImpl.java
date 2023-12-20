package org.example.service.impl;

import org.example.domain.abstraction.Animal;
import org.example.factory.AnimalSimpleFactory;
import org.example.service.CreateAnimalService;

import java.util.ArrayList;
import java.util.List;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    @Override
    public List<Animal> createRandomAnimals() {
        int maxCount = 10;
        List<Animal> animals = new ArrayList<>(maxCount);

        System.out.println("It's override method in class CreateAnimalServiceImpl");

        int count = 0;
        Animal animal;
        do {
            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals.add(animal);

        } while (count++ < maxCount);

        return animals;
    }

    public List<Animal> createRandomAnimals(int n) {
        List<Animal> animals = new ArrayList<>(n);
        System.out.println("It's overload method in class CreateAnimalServiceImpl");

        Animal animal;
        for (int i = 0; i < n; i++) {
            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals.add(animal);
        }

        return animals;
    }

}
