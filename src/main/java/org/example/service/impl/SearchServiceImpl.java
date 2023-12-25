package org.example.service.impl;

import org.example.domain.abstraction.Animal;
import org.example.service.SearchService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchServiceImpl implements SearchService {

    /**
     * Finds animals which were born is leap year and returns an array of their names
     *
     * @param animals An input array of animals
     * @return Array of animals' names which were born in leap year
     */
    @Override
    public String[] findLeapYearNames(Animal[] animals) {
        if (animals == null) {
            throw new IllegalArgumentException("Argument Animal[] animals must not be null!");
        }

        List<String> names = new ArrayList<>();
        for (Animal animal : animals) {
            LocalDate birthdate = animal.getBirthDate();
            if (birthdate.isLeapYear()) {
                names.add(animal.getName());
            }
        }

        return names.toArray(new String[0]);
    }


    /**
     * Finds animals older than argument n
     *
     * @param animals An input array of animals
     * @param n       Represent years count
     * @return Array of animals which are older than argument n
     */
    @Override
    public Animal[] findOlderAnimal(Animal[] animals, int n) {
        if (animals == null) {
            throw new IllegalArgumentException("Argument Animal[] animals must not be null!");
        }

        List<Animal> animalsOlderN = new ArrayList<>();
        var now = LocalDate.now();

        for (Animal animal : animals) {
            if (Period.between(animal.getBirthDate(), now).getYears() > n) {
                animalsOlderN.add(animal);
            }
        }

        return animalsOlderN.toArray(new Animal[0]);
    }

    /**
     * Find duplicate animals and prints them in {@code System.out}
     *
     * @param animals An input array of animals
     */
    @Override
    public void findDuplicate(Animal[] animals) {
        if (animals == null) {
            throw new IllegalArgumentException("Argument Animal[] animals must not be null!");
        }

        //не для "уникальности", а для скорости поиска
        Set<Animal> animalsUnique = new HashSet<>();
        for (Animal animal : animals) {
            if (animalsUnique.contains(animal)) {
                System.out.println(animal);
            } else {
                animalsUnique.add(animal);
            }
        }

    }

}
