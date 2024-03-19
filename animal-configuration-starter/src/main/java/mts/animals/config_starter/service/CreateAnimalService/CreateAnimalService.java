package mts.animals.config_starter.service.CreateAnimalService;

import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.factory.animal.AnimalSimpleFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CreateAnimalService {

    String NAME = "example_CreateAnimalService";

    void setAnimalType(AnimalType animalType);

    AnimalType getAnimalType();

    Animal createAnimal();

    /**
     * Creates a map of 10 randomly generated animals with use of AnimalSimpleFactory,
     * where key is the type of animal created and value is animals created with this type
     */
    default Map<String, List<Animal>> createRandomAnimals() {
        int maxCount = 10;
        Map<String, List<Animal>> animals = new HashMap<>(AnimalType.values().length);

        System.out.println("It's default method in interface CreateAnimalService");

        int count = 0;
        Animal animal;
        while (count != maxCount) {
            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals.computeIfAbsent(animal.getBreed(), k -> new ArrayList<>());

            animals.get(animal.getBreed()).add(animal);
            count++;
        }

        return animals;
    }

}
