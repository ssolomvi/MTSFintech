package mts.animals.app.service.impl;

import mts.animals.app.config.AppConfigProperties;
import mts.animals.app.exceptions.AppArrayIncorrectLength;
import mts.animals.app.exceptions.AppIllegalArgumentException;
import mts.animals.app.exceptions.AppNullPointerException;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.provider.CreateAnimalServiceProvider;
import mts.animals.config_starter.service.CreateAnimalService.CreateAnimalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnimalsRepositoryImpl implements AnimalsRepository {

    private static final Logger log = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);

    private static final int SIZE_OF_ANIMALS_LIST = 1000;

    private final CopyOnWriteArrayList<Animal> animals = new CopyOnWriteArrayList<>();

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
                    throw new AppNullPointerException(String.format("Caramba! All hands on deck! 'prototype' at index {%d} is null", i));
                }

                prototypes.add(prototype);
            }

            int randInt;
            for (int i = 0; i < SIZE_OF_ANIMALS_LIST; i++) {
                randInt = ThreadLocalRandom.current().nextInt(0, 3);
                animals.add(prototypes.get(randInt).createAnimal());
            }

            initialized = true;
        }

    }

    /**
     * Finds animals which were born is leap year and returns an array of their names
     *
     * @return Map, where key is animal's breed +  animals' name and value is its birthday
     */
    @Override
    public ConcurrentMap<String, LocalDate> findLeapYearNames() {
        Map<String, LocalDate> map =
                animals.stream()
                        .filter(Objects::nonNull)
                        .filter(animal -> {
                            LocalDate birthday = animal.getBirthDate();
                            return Objects.nonNull(birthday) && birthday.isLeapYear();
                        })
                        .collect(Collectors.toMap(animal -> animal.getBreed() + " " + animal.getName(), Animal::getBirthDate));

        return new ConcurrentHashMap<>(map);
    }


    /**
     * Finds animals older than argument n. If there are no animals, older than n,
     * returns the oldest animal
     *
     * @param n Represent years count
     * @return Map, where key is  of animals which are older than argument n
     */
    @Override
    public ConcurrentMap<Animal, Integer> findOlderAnimal(int n) {
        if (n <= 0) {
            throw new AppIllegalArgumentException("AnimalRepositoryImpl::findOlderAnimal: param n cannot be less or equal to 0");
        }
        var now = LocalDate.now();

        Map<Animal, Integer> animalsOlderNMap = animals.stream()
                .filter(Objects::nonNull)
                .map(animal -> {
                    LocalDate birthday = animal.getBirthDate();
                    if (Objects.nonNull(birthday)) {
                        Period betweenNowAndBirthdate = Period.between(birthday, now);
                        int timeGap = betweenNowAndBirthdate.getYears();

                        if (timeGap > n ||
                                (timeGap == n && (betweenNowAndBirthdate.getMonths() > 0 || betweenNowAndBirthdate.getDays() > 0))) {
                            return new AbstractMap.SimpleEntry<>(animal, timeGap);
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!animalsOlderNMap.isEmpty()) {
            return new ConcurrentHashMap<>(animalsOlderNMap);
        }

        Map<Animal, Integer> map =
                animals.stream()
                        .filter(Objects::nonNull)
                        .map(animal -> {
                            LocalDate birthday = animal.getBirthDate();
                            if (Objects.nonNull(birthday)) {
                                int timeGap = Period.between(birthday, now).getYears();
                                return new AbstractMap.SimpleEntry<>(animal, timeGap);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .max(Comparator.comparingInt(Map.Entry::getValue))
                        .stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new ConcurrentHashMap<>(map);
    }

    /**
     * Find duplicate animals and prints them in {@code System.out}
     *
     * @return Found duplicates and times the certain duplicate was met
     */
    @Override
    public ConcurrentMap<String, List<Animal>> findDuplicate() {
        Map<String, List<Animal>> map =
                animals.stream()
                        .filter(Objects::nonNull)
                        .filter(animal -> Collections.frequency(animals, animal) > 1)
                        .collect(Collectors.groupingBy(Animal::getBreed));
        return new ConcurrentHashMap<>(map);
    }

    /**
     * Find average age of {@code animals}
     *
     * @param input input list of animals
     * @return Average age of animals in list {@code animals}
     */
    @Override
    public float findAverageAge(List<Animal> input) {
        if (input == null) {
            throw new AppIllegalArgumentException("AnimalsRepositoryImpl::findAverageAge: param input should not be null");
        }

        AtomicInteger animalsWithNonNullBirthDates = new AtomicInteger();
        LocalDate now = LocalDate.now();
        return (float) input.stream()
                .filter(Objects::nonNull)
                .mapToInt(animal -> {
                    LocalDate birthday = animal.getBirthDate();
                    if (Objects.nonNull(birthday)) {
                        animalsWithNonNullBirthDates.getAndIncrement();
                        return Period.between(birthday, now).getYears();
                    }
                    return 0;
                })
                .sum() / animalsWithNonNullBirthDates.get();
        // another way: .summaryStatistics(); .getAverage();
    }

    private BigDecimal findAverageCost(List<Animal> input) {
        if (input == null) {
            throw new AppIllegalArgumentException("AnimalsRepositoryImpl::findAverageCost: param input should not be null");
        }

        AtomicInteger animalsWithNonNullCost = new AtomicInteger();

        return input.stream()
                .filter(Objects::nonNull)
                .map(animal -> {
                    BigDecimal cost = animal.getCost();
                    if (Objects.nonNull(cost)) {
                        animalsWithNonNullCost.getAndIncrement();
                        return cost;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(animalsWithNonNullCost.get()), RoundingMode.HALF_EVEN);
    }

    /**
     * Finds animals in {@code animals} which age is > 5 years and cost is more than average
     *
     * @param input input list of animals
     * @return Animals older than 5 years and more expensive than average
     */
    // todo: must the return type be just List<Animal>?
    @Override
    public CopyOnWriteArrayList<Animal> findOldAndExpensive(List<Animal> input) {
        if (input == null) {
            throw new AppIllegalArgumentException("AnimalsRepositoryImpl::findOldAndExpensive: param input should not be null");
        }

        BigDecimal averageCost = findAverageCost(input);
        LocalDate now = LocalDate.now();

        List<Animal> list =
                input.stream()
                        .filter(Objects::nonNull)
                        .filter(animal -> {
                            /* should I use variables or should I use methods invocations? pluses, minuses? */
                            LocalDate birthday = animal.getBirthDate();
                            BigDecimal cost = animal.getCost();
                            if (Objects.nonNull(birthday) && Objects.nonNull(cost)) {
                                Period timeGap = Period.between(birthday, now);
                                int age = timeGap.getYears();
                                return (age > 5
                                        || (age == 5 && (timeGap.getDays() > 0 || timeGap.getMonths() > 0)))
                                        && animal.getCost().compareTo(averageCost) > 0;
                            }
                            return false;
                        })
                        .sorted(Comparator.comparing(Animal::getBirthDate))
                        .toList();
        return new CopyOnWriteArrayList<>(list);
    }

    /**
     * Finds 3 animals with the lowest price and returns their names
     *
     * @param input input list of animals
     * @return Names of animals with the lowest price sorted in reverse alphabetical order.
     */
    // todo: must the return type be just List<Animal>?
    @Override
    public CopyOnWriteArrayList<String> findMinCostAnimals(List<Animal> input) throws AppArrayIncorrectLength {
        if (input == null) {
            throw new AppIllegalArgumentException("AnimalsRepositoryImpl::findMinCostAnimals: param input should not be null");
        }

        if (input.size() < 3) {
            throw new AppArrayIncorrectLength("AnimalsRepositoryImpl::findMinCostAnimals: param input has length < 3");
        }

        List<Animal> toReturn = input.stream()
                .filter(Objects::nonNull)
                .filter(animal -> animal.getCost() != null)
                .sorted(Comparator.comparing(Animal::getCost))
                .toList();
        if (toReturn.size() < 3) {
            // what should I do here?..
            return new CopyOnWriteArrayList<>();
        }

        List<String> list =
        toReturn.stream()
                .limit(3)
                .map(Animal::getName)
                .sorted((s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s2, s1))
                .toList();
        return new CopyOnWriteArrayList<>(list);
    }

    @Override
    public void printDuplicate() {
        Map<String, List<Animal>> duplicates = findDuplicate();
        var sb = new StringBuilder();

        if (Objects.nonNull(duplicates) && !duplicates.isEmpty()) {
            for (Map.Entry<String, List<Animal>> entry : duplicates.entrySet()) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("\n");
            }
        }

        if (logDebugData) {
            log.info(sb.toString());
        } else {
            System.out.println(sb);
        }

    }

}
