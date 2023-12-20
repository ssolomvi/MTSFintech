package org.example;

import org.example.impl.CreateAnimalServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CreateAnimalServiceImpl createAnimalService = new CreateAnimalServiceImpl();
        List<Animal> resultDefaultMethod = new CreateAnimalService(){}.createUniqueAnimals();
        List<Animal> resultOverloadMethod = createAnimalService.createUniqueAnimals(5);
        List<Animal> resultOverrideMethod = createAnimalService.createUniqueAnimals();
    }
}