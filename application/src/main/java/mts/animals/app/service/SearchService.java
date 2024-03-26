package mts.animals.app.service;

import mts.animals.config_starter.abstraction.Animal;

public interface SearchService {

    String[] findLeapYearNames(Animal[] animals);

    Animal[] findOlderAnimal(Animal[] animals, int n);

    Animal[] findDuplicate(Animal[] animals);

}
