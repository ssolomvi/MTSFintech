package org.example.service;

import org.example.domain.abstraction.Animal;

public interface SearchService {

    String[] findLeapYearNames(Animal[] animals);

    Animal[] findOlderAnimal(Animal[] animals, int n);

    Animal[] findDuplicate(Animal[] animals);

}
