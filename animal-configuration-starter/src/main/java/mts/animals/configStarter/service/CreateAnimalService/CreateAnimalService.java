package mts.animals.configStarter.service.CreateAnimalService;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.factory.animal.AnimalSimpleFactory;

public interface CreateAnimalService {

    String NAME = "example_CreateAnimalService";

    void setAnimalType(AnimalType animalType);

    Animal createAnimal();

    /**
     * Creates an array of size 10 of randomly generated animals with use of AnimalSimpleFactory
     */
    default Animal[] createRandomAnimals() {
        int maxCount = 10;
        Animal[] animals = new Animal[maxCount];

        System.out.println("It's default method in interface CreateAnimalService");

        int count = 0;
        Animal animal;
        while (count != maxCount) {
            animal = AnimalSimpleFactory.createRandomAnimal();
            System.out.println(animal);

            animals[count++] = animal;
        }

        return animals;
    }

}
