package mts.animals.app.service.impl;

import mts.animals.app.exceptions.AppIllegalArgumentException;
import mts.animals.app.service.SearchService;
import mts.animals.config_starter.abstraction.Animal;

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
            throw new AppIllegalArgumentException("SearchServiceImpl::findLeapYearNames: Argument Animal[] animals must not be null!");
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
            throw new AppIllegalArgumentException("SearchServiceImpl::findOlderAnimal: Argument Animal[] animals must not be null!");
        }

        List<Animal> animalsOlderN = new ArrayList<>();
        var now = LocalDate.now();

        for (Animal animal : animals) {
            Period betweenNowAndBirthdate = Period.between(animal.getBirthDate(), now);
            int yearsBetweenNowAndAnimalsBirthdate = betweenNowAndBirthdate.getYears();
            if (yearsBetweenNowAndAnimalsBirthdate > n) {
                animalsOlderN.add(animal);
            } else if (yearsBetweenNowAndAnimalsBirthdate == n
                    && (betweenNowAndBirthdate.getDays() != 0 || betweenNowAndBirthdate.getMonths() != 0)) {
                animalsOlderN.add(animal);
            }
        }

        return animalsOlderN.toArray(new Animal[0]);
    }

    private void printDuplicate(List<Animal> duplicates) {
        if (duplicates == null) {
            throw new AppIllegalArgumentException("SearchServiceImpl::printDuplicate: Argument Animal[] animals must not be null!");
        }

        for (Animal duplicate : duplicates) {
            System.out.println(duplicate);
        }
    }

    /**
     * Find duplicate animals and prints them in {@code System.out}
     *
     * @param animals An input array of animals
     * @return Found duplicates
     */
    @Override
    public Animal[] findDuplicate(Animal[] animals) {
        if (animals == null) {
            throw new AppIllegalArgumentException("SearchServiceImpl::findDuplicate: Argument Animal[] animals must not be null!");
        }

        List<Animal> duplicates = new ArrayList<>();
        // not for "uniqueness", but for search speed
        Set<Animal> animalsUnique = new HashSet<>();
        for (Animal animal : animals) {
            if (animalsUnique.contains(animal)) {
                duplicates.add(animal);
            } else {
                animalsUnique.add(animal);
            }
        }

        printDuplicate(duplicates);

        return duplicates.toArray(new Animal[0]);
    }

}
