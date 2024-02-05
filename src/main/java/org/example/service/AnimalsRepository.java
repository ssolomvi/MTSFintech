package org.example.service;

import org.example.domain.abstraction.Animal;

public interface AnimalsRepository {
    String[] findLeapYearNames();

    Animal[] findOlderAnimal(int n);

    Animal[] findDuplicate();
}
