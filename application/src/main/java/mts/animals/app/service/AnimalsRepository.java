package mts.animals.app.service;

import mts.animals.configStarter.abstraction.Animal;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AnimalsRepository {

    String NAME = "example_AnimalsRepository";

    Map<String, LocalDate> findLeapYearNames();

    Map<Animal, Integer> findOlderAnimal(int n);

    Map<String, List<Animal>> findDuplicate();

    float findAverageAge(List<Animal> animals);

    List<Animal> findOldAndExpensive(List<Animal> animals);

    List<String> findMinCostAnimals(List<Animal> animals);

    void printDuplicate();

}
