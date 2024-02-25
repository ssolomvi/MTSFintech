package mts.animals.configStarter.service.CreateAnimalService;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.factory.animal.AnimalFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    private final AnimalFactory animalFactory;
    private AnimalType animalType;

    @Autowired
    public CreateAnimalServiceImpl(AnimalFactory animalFactory) {
        if (animalFactory == null) {
            throw new IllegalArgumentException("'animalFactory' is null");
        }

        this.animalFactory = animalFactory;
    }

    @Override
    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    @Override
    public AnimalType getAnimalType() {
        return animalType;
    }

    @Override
    public Animal createAnimal() {
        return animalFactory.createRandomAnimal(animalType);
    }

    /**
     * Creates a map of 10 randomly generated animals with use of AnimalSimpleFactory,
     * where key is the type of animal created and value is animals created with this type
     */
    @Override
    public Map<String, List<Animal>> createRandomAnimals() {
        int maxCount = 10;
        Map<String, List<Animal>> animals = new HashMap<>(AnimalType.values().length);

        System.out.println("It's override method in class CreateAnimalServiceImpl");

        int count = 0;
        Animal animal;
        do {
            animal = animalFactory.createRandomAnimal(animalType);
            System.out.println(animal);

            animals.computeIfAbsent(animal.getBreed(), k -> new ArrayList<>());

            animals.get(animal.getBreed()).add(animal);
            count++;
        } while (count < maxCount);

        return animals;
    }

    /**
     * Creates a map of n randomly generated animals with use of AnimalSimpleFactory,
     * where key is the type of animal created and value is animals created with this type
     */
    public Map<String, List<Animal>> createRandomAnimals(int n) {
        Map<String, List<Animal>> animals = new HashMap<>(AnimalType.values().length);

        System.out.println("It's overload method in class CreateAnimalServiceImpl");

        Animal animal;
        for (int i = 0; i < n; i++) {
            animal = animalFactory.createRandomAnimal(animalType);
            System.out.println(animal);

            animals.computeIfAbsent(animal.getBreed(), k -> new ArrayList<>());
            animals.get(animal.getBreed()).add(animal);
        }

        return animals;
    }

}
