package org.example.impl;

import org.example.Animal;
import org.example.CreateAnimalService;

import java.util.ArrayList;
import java.util.List;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    public List<Animal> createUniqueAnimals(int N) {
        List<Animal> animals = new ArrayList<>();
        System.out.println("It's overload method in class CreateAnimalServiceImpl");
        for (int i = 0; i < N; i++) {
            Animal newAnimal = createUniqueAnimal();
            System.out.println(newAnimal);
            animals.add(newAnimal);
        }
        return animals;
    }

    @Override
    public List<Animal> createUniqueAnimals() {
        List<Animal> animals = new ArrayList<>();
        int iter = 0;
        System.out.println("It's override method in class CreateAnimalServiceImpl");
        do {
            Animal newAnimal = createUniqueAnimal();
            System.out.println(newAnimal);
            animals.add(newAnimal);
        } while (iter++ < 10);
        return animals;
    }
}
