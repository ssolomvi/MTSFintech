package mts.animals.app.service;

import mts.animals.configStarter.abstraction.Animal;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface AnimalsRepository {

    String NAME = "example_AnimalsRepository";

    Map<String, LocalDate> findLeapYearNames();

    Map<Animal, Integer> findOlderAnimal(int n);

    Map<String, Integer> findDuplicate();

    void printDuplicate();

}
