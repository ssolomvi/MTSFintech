package org.example.service.impl;

import org.example.domain.abstraction.Animal;
import org.example.service.SearchService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements SearchService {
    /**Finds animals which were born is leap year and returns an array of their names
     * @param animals An input array of animals
     * @return Array of animals' names which were born in leap year*/
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
        String[] namesArray = new String[names.size()];
        return names.toArray(namesArray);
    }


    /** Finds animals older than argument n
     * @param animals An input array of animals
     * @param n Represent years count
     * @return Array of animals which are older than argument n*/
    @Override
    public Animal[] findOlderAnimal(Animal[] animals, int n) {
        if (animals == null) {
            throw new IllegalArgumentException("Argument Animal[] animals must not be null!");
        }
        List<Animal> animalsOlderN = new ArrayList<>();
        for (Animal animal : animals) {
            if (Period.between(animal.getBirthDate(), LocalDate.now()).getYears() > n) {
                animalsOlderN.add(animal);
            }
        }
        Animal[] toReturn = new Animal[animalsOlderN.size()];
        return animalsOlderN.toArray(toReturn);
    }

    /** Find duplicate animals and prints them in {@code System.out}
     * @param animals An input array of animals
     * */
    @Override
    public void findDuplicate(Animal[] animals) {
        if (animals == null) {
            throw new IllegalArgumentException("Argument Animal[] animals must not be null!");
        }
        List<Animal> animalsUnique = new ArrayList<>();
        for (Animal animal : animals) {
            if (animalsUnique.contains(animal)) {
                System.out.println(animal);
            } else {
                animalsUnique.add(animal);
            }
        }
    }
}
