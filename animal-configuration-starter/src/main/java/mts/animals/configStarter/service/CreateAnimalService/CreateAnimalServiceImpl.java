package mts.animals.configStarter.service.CreateAnimalService;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.factory.animal.AnimalFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    private final AnimalFactory animalFactory;
    private AnimalType animalType;

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    @Autowired
    public CreateAnimalServiceImpl(AnimalFactory animalFactory) {
        if (animalFactory == null) {
            throw new IllegalArgumentException("'animalFactory' is null");
        }

        this.animalFactory = animalFactory;
    }

    @Override
    public Animal createAnimal() {
        return animalFactory.createRandomAnimal(animalType);
    }

    /**
     * Creates an array of size 10 of randomly generated animals with use of AnimalSimpleFactory
     */
    @Override
    public Animal[] createRandomAnimals() {
        int maxCount = 10;
        Animal[] animals = new Animal[maxCount];

        System.out.println("It's override method in class CreateAnimalServiceImpl");

        int count = 0;
        Animal animal;
        do {
            animal = animalFactory.createRandomAnimal();
            System.out.println(animal);

            animals[count++] = animal;

        } while (count < maxCount);

        return animals;
    }

    /**
     * Creates an array of size n of randomly generated animals with use of AnimalSimpleFactory
     */
    public Animal[] createRandomAnimals(int n) {
        Animal[] animals = new Animal[n];
        System.out.println("It's overload method in class CreateAnimalServiceImpl");

        Animal animal;
        for (int i = 0; i < n; i++) {
            animal = animalFactory.createRandomAnimal();
            System.out.println(animal);

            animals[i] = animal;
        }

        return animals;
    }

}
