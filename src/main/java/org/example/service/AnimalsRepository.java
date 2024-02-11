package org.example.service;

import org.example.domain.abstraction.Animal;

import java.util.Set;

public interface AnimalsRepository {

    String NAME = "example_AnimalsRepository";

    String[] findLeapYearNames();

    Animal[] findOlderAnimal(int n);

    Set<Animal> findDuplicate();

    void printDuplicate();

}
