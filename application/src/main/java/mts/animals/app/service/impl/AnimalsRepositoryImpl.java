package mts.animals.app.service.impl;

import mts.animals.app.config.AppConfigProperties;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalsRepositoryImpl implements AnimalsRepository {

    private static final Logger log = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);

    private final List<Animal> animals = new ArrayList<>(100);

    private final CreateAnimalServiceProvider createAnimalServiceProvider;

    private final boolean logDebugData;

    private boolean initialized;

    public AnimalsRepositoryImpl(CreateAnimalServiceProvider createAnimalServiceProvider,
                                 AppConfigProperties appConfigProperties) {
        this.createAnimalServiceProvider = createAnimalServiceProvider;

        this.logDebugData = Optional.ofNullable(appConfigProperties)
                .map(AppConfigProperties::getLogDebugData)
                .orElseThrow();
    }

    public void postConstruct() {
        if (!initialized) {
            int countOfAnimalTypes = AnimalType.values().length;
            List<CreateAnimalService> prototypes = new ArrayList<>(countOfAnimalTypes);
            for (int i = 0; i < countOfAnimalTypes; i++) {
                CreateAnimalService prototype = createAnimalServiceProvider.createCreateAnimalService();
                if (Objects.isNull(prototype)) {
                    throw new RuntimeException(String.format("Caramba! All hands on deck! 'prototype' at index {%d} is null", i));
                }

                prototypes.add(prototype);
            }

            int randInt;
            for (int i = 0; i < animals.size(); i++) {
                randInt = ThreadLocalRandom.current().nextInt(0, 3);
                animals.add(prototypes.get(randInt).createAnimal());
            }

            initialized = true;
        }

    }

    /**
     * Finds animals which were born is leap year and returns an array of their names
     *
     * @return Map, where key is animal's type +  animals' name and value is its birthday
     */
    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        Map<String, LocalDate> namesAndBirthdays = new HashMap<>();
        for (Animal animal : animals) {
            if (Objects.nonNull(animal)) {
                LocalDate birthdate = animal.getBirthDate();
                if (Objects.nonNull(birthdate) && birthdate.isLeapYear()) {
                    namesAndBirthdays.put(animal.getBreed() + " " + animal.getName(),
                            animal.getBirthDate());
                }
            }
        }

        return namesAndBirthdays;
    }


    /**
     * Finds animals older than argument n. If there are no animals, older than n,
     * returns the oldest animal
     *
     * @param n Represent years count
     * @return Map, where key is  of animals which are older than argument n
     */
    @Override
    public Map<Animal, Integer> findOlderAnimal(int n) {
        // todo: if not use animals array in future commits, check if the input array is empty
        if (n <= 0) {
            throw new IllegalArgumentException("param n cannot be less or equal to 0");
        }

        Map<Animal, Integer> animalsOlderThanN = new HashMap<>();
        Animal theOldestAnimal = null;
        int theOldestAnimalAge = 0;
        var now = LocalDate.now();

        for (Animal animal : animals) {
            if (Objects.nonNull(animal)) {
                var birthDate = animal.getBirthDate();
                if (Objects.nonNull(birthDate)) {
                    Period betweenNowAndBirthdate = Period.between(birthDate, now);
                    int yearsBetweenNowAndAnimalsBirthdate = betweenNowAndBirthdate.getYears();

                    if (yearsBetweenNowAndAnimalsBirthdate > n) {
                        animalsOlderThanN.put(animal, yearsBetweenNowAndAnimalsBirthdate);

                    } else if (yearsBetweenNowAndAnimalsBirthdate == n
                            && (betweenNowAndBirthdate.getDays() != 0 || betweenNowAndBirthdate.getMonths() != 0)) {
                        animalsOlderThanN.put(animal, yearsBetweenNowAndAnimalsBirthdate);

                    } else if (yearsBetweenNowAndAnimalsBirthdate > theOldestAnimalAge) {
                        theOldestAnimal = animal;
                        theOldestAnimalAge = yearsBetweenNowAndAnimalsBirthdate;
                    }
                }
            }
        }

        if (animalsOlderThanN.isEmpty()) {
            animalsOlderThanN.put(theOldestAnimal, theOldestAnimalAge);
        }

        return animalsOlderThanN;
    }

    /**
     * Find duplicate animals and prints them in {@code System.out}
     *
     * @return Found duplicates and times the certain duplicate was met
     */
    @Override
    public Map<String, Integer> findDuplicate() {
        Map<String, Integer> duplicates = new HashMap<>();
        // not for "uniqueness", but for search speed
        Set<Animal> animalsUnique = new HashSet<>();
        for (Animal animal : animals) {
            if (Objects.nonNull(animal)) {
                if (animalsUnique.contains(animal)) {
                    if (duplicates.containsKey(animal.getBreed())) {
                        duplicates.put(animal.getBreed(), duplicates.get(animal.getBreed()) + 1);
                    } else {
                        duplicates.put(animal.getBreed(), 1);
                    }
                } else {
                    animalsUnique.add(animal);
                }
            }
        }

        return duplicates;
    }

    @Override
    public void printDuplicate() {
        Map<String, Integer> duplicates = findDuplicate();
        if (Objects.nonNull(duplicates) && !duplicates.isEmpty()) {

            for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
                if (logDebugData) {
                    log.info(entry.getKey() + "=" + entry.getValue());
                } else {
                    System.out.println(entry.getKey() + "=" + entry.getValue());
                }

            }
        }

    }

}
