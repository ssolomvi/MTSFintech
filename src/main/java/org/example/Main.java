package org.example;

import org.example.domain.abstraction.Animal;
import org.example.factory.animal.AnimalFactoryImpl;
import org.example.service.CreateAnimalService;
import org.example.service.SearchService;
import org.example.service.impl.CreateAnimalServiceImpl;
import org.example.service.impl.SearchServiceImpl;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl(new AnimalFactoryImpl());
        SearchService searchService = new SearchServiceImpl();

        Animal[] resultDefaultMethod = new CreateAnimalService() {
        }.createRandomAnimals();
        System.out.println("Names of animals born in leap years:");

        String[] animalsNames = searchService.findLeapYearNames(resultDefaultMethod);
        System.out.println(Arrays.toString(animalsNames));

        Animal[] resultOverrideMethod = createAnimalService.createRandomAnimals();
        int n = 3;
        System.out.println("Animals which are older than " + 3 + " years:");
        Animal[] animalsOlderN = searchService.findOlderAnimal(resultOverrideMethod, n);
        System.out.println(Arrays.toString(animalsOlderN));

        if (createAnimalService instanceof CreateAnimalServiceImpl) {
            Animal[] resultOverloadMethod =
                    ((CreateAnimalServiceImpl) createAnimalService).createRandomAnimals(100);

            System.out.println("Print 'resultOverloadMethod' result");
            System.out.println(Arrays.toString(resultOverloadMethod));
            System.out.println("Duplicate animals:");
            searchService.findDuplicate(resultOverloadMethod);
        }

    }

}