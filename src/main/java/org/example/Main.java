package org.example;

import org.example.domain.abstraction.Animal;
import org.example.service.CreateAnimalService;
import org.example.service.impl.CreateAnimalServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl();

        List<Animal> resultDefaultMethod = new CreateAnimalService() {
        }.createRandomAnimals();

        List<Animal> resultOverrideMethod = createAnimalService.createRandomAnimals();
        if (createAnimalService instanceof CreateAnimalServiceImpl) {
            List<Animal> resultOverloadMethod =
                    ((CreateAnimalServiceImpl) createAnimalService).createRandomAnimals(5);
        }

    }

}