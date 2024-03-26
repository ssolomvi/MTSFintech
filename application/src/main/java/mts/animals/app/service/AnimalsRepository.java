package mts.animals.app.service;

import mts.animals.app.exceptions.AppArrayIncorrectLength;
import mts.animals.config_starter.abstraction.Animal;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface AnimalsRepository {

    String NAME = "example_AnimalsRepository";

    ConcurrentMap<String, LocalDate> findLeapYearNames();

    ConcurrentMap<Animal, Integer> findOlderAnimal(int n);

    ConcurrentMap<String, List<Animal>> findDuplicate();

    float findAverageAge(List<Animal> animals);

    float findAverageAge();

    CopyOnWriteArrayList<Animal> findOldAndExpensive(List<Animal> animals);

    CopyOnWriteArrayList<String> findMinCostAnimals(List<Animal> animals) throws AppArrayIncorrectLength;

    void printDuplicate();

}
