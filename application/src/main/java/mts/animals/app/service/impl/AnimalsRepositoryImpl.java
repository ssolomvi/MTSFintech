package mts.animals.app.service.impl;

import mts.animals.app.config.AppConfigProperties;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalsRepositoryImpl implements AnimalsRepository {

    private static final Logger log = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);

    private final Animal[] animals = new Animal[100];

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
            for (int i = 0; i < animals.length; i++) {
                randInt = ThreadLocalRandom.current().nextInt(0, 3);
                animals[i] = prototypes.get(randInt).createAnimal();
            }

            initialized = true;
        }

    }

    /**
     * Finds animals which were born is leap year and returns an array of their names
     *
     * @return Array of animals' names which were born in leap year
     */
    @Override
    public String[] findLeapYearNames() {
        List<String> names = new ArrayList<>();
        for (Animal animal : animals) {
            if (Objects.nonNull(animal)) {
                LocalDate birthdate = animal.getBirthDate();
                if (Objects.nonNull(birthdate)
                        && birthdate.isLeapYear()) {

                    names.add(animal.getName());
                }
            }
        }

        return names.toArray(new String[0]);
    }


    /**
     * Finds animals older than argument n
     *
     * @param n Represent years count
     * @return Array of animals which are older than argument n
     */
    @Override
    public Animal[] findOlderAnimal(int n) {
        List<Animal> animalsOlderN = new ArrayList<>();
        var now = LocalDate.now();

        for (Animal animal : animals) {
            if (Objects.nonNull(animal)) {
                var birthDate = animal.getBirthDate();
                if (Objects.nonNull(birthDate)) {
                    Period betweenNowAndBirthdate = Period.between(birthDate, now);
                    int yearsBetweenNowAndAnimalsBirthdate = betweenNowAndBirthdate.getYears();
                    if (yearsBetweenNowAndAnimalsBirthdate > n) {
                        animalsOlderN.add(animal);

                    } else if (yearsBetweenNowAndAnimalsBirthdate == n
                            && (betweenNowAndBirthdate.getDays() != 0 || betweenNowAndBirthdate.getMonths() != 0)) {
                        animalsOlderN.add(animal);
                    }

                }
            }

        }

        return animalsOlderN.toArray(new Animal[0]);
    }

    /**
     * Find duplicate animals and prints them in {@code System.out}
     *
     * @return Found duplicates
     */
    @Override
    public Set<Animal> findDuplicate() {
        Set<Animal> duplicates = new LinkedHashSet<>();
        // not for "uniqueness", but for search speed
        Set<Animal> animalsUnique = new HashSet<>();
        for (Animal animal : animals) {
            if (Objects.nonNull(animal)) {
                if (animalsUnique.contains(animal)) {
                    duplicates.add(animal);
                } else {
                    animalsUnique.add(animal);
                }
            }
        }

        return duplicates;
    }

    @Override
    public void printDuplicate() {
        Set<Animal> duplicates = findDuplicate();
        if (Objects.nonNull(duplicates)
                && !duplicates.isEmpty()) {

            for (Animal duplicate : duplicates) {
                if (logDebugData) {
                    log.info(String.valueOf(duplicate));
                } else {
                    System.out.println(duplicate);
                }

            }
        }

    }

}
