package mts.animals.app.service.impl;

import mts.animals.app.config.AppConfigProperties;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnimalsRepositoryImpl implements AnimalsRepository {

    private static final Logger log = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);

    private static final int SIZE_OF_ANIMALS_LIST = 1000;

    private final List<Animal> animals = new ArrayList<>(SIZE_OF_ANIMALS_LIST);

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
                    throw new NullPointerException(String.format("Caramba! All hands on deck! 'prototype' at index {%d} is null", i));
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
    public Map<String, LocalDate> findLeapYearNames() {
        return animals.stream()
                .filter(Objects::nonNull)
                .filter(animal -> {
                    LocalDate birthday = animal.getBirthDate();
                    return Objects.nonNull(birthday) && birthday.isLeapYear();
                })
                .collect(Collectors.toMap(animal -> animal.getBreed() + " " + animal.getName(), Animal::getBirthDate));
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
        if (n <= 0) {
            throw new IllegalArgumentException("param n cannot be less or equal to 0");
        }
        var now = LocalDate.now();

        Map<Animal, Integer> animalsOlderN = animals.stream()
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

        if (!animalsOlderN.isEmpty()) {
            return animalsOlderN;
        }
        return animals.stream()
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
    }

    /**
     * Find duplicate animals and prints them in {@code System.out}
     *
     * @return Found duplicates and times the certain duplicate was met
     */
    @Override
    public Map<String, List<Animal>> findDuplicate() {
        return animals.stream()
                .filter(Objects::nonNull)
                .filter(animal -> Collections.frequency(animals, animal) > 1)
                .collect(Collectors.groupingBy(Animal::getBreed));
    }

    /**
     * Find average age of {@code animals}
     *
     * @param input input list of animals
     * @return Average age of animals in list {@code animals}
     */
    @Override
    public float findAverageAge(List<Animal> input) {
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
    @Override
    public List<Animal> findOldAndExpensive(List<Animal> input) {
        BigDecimal averageCost = findAverageCost(input);
        LocalDate now = LocalDate.now();

        return input.stream()
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
    }

    /**
     * Finds 3 animals with the lowest price and returns their names
     * @param input input list of animals
     * @return Names of animals with the lowest price sorted in reverse alphabetical order.
     * */
    @Override
    public List<String> findMinCostAnimals(List<Animal> input) {
        List<Animal> toReturn = input.stream()
                .filter(Objects::nonNull)
                .filter(animal -> animal.getCost() != null)
                .sorted(Comparator.comparing(Animal::getCost))
                .toList();
        if (toReturn.size() < 3) {
            // what should I do here?..
            return Collections.emptyList();
        }

        return toReturn.stream()
                .limit(3)
                .map(Animal::getName)
                .sorted((s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s2, s1))
                .toList();
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
