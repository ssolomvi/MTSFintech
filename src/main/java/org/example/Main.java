package org.example;

import org.example.domain.abstraction.Animal;
import org.example.service.CreateAnimalService;
import org.example.service.SearchService;
import org.example.service.impl.CreateAnimalServiceImpl;
import org.example.service.impl.SearchServiceImpl;

public class Main {

    public static void main(String[] args) {
        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl();
        SearchService searchService = new SearchServiceImpl();

        Animal[] resultDefaultMethod = new CreateAnimalService() {
        }.createRandomAnimals();
        System.out.println("Names of animals born in leap years:");
        String[] animalsNames = searchService.findLeapYearNames(resultDefaultMethod);
        for (String name : animalsNames) {
            System.out.println(name);
        }

        Animal[] resultOverrideMethod = createAnimalService.createRandomAnimals();
        int n = 3;
        System.out.println("Animals which are older than " + 3 + " years:");
        Animal[] animalsOlderN = searchService.findOlderAnimal(resultOverrideMethod, n);
        for (Animal animal : animalsOlderN) {
            System.out.println(animal);
        }

        if (createAnimalService instanceof CreateAnimalServiceImpl) {
            Animal[] resultOverloadMethod =
                    ((CreateAnimalServiceImpl) createAnimalService).createRandomAnimals(100);
            System.out.println("Duplicate animals:");
            searchService.findDuplicate(resultOverloadMethod);
        }

    }

}