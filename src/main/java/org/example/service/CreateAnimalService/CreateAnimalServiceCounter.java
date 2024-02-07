package org.example.service.CreateAnimalService;

import org.example.domain.enums.AnimalType;
import org.example.factory.animal.AnimalFactory;

import java.util.HashMap;

public final class CreateAnimalServiceCounter {
    private static final int maxCount = AnimalType.values().length;
    private static int counter = 0;

    private static CreateAnimalService createAnimalService;

    private static final HashMap<CreateAnimalService, AnimalType> createdServicePrototypes = new HashMap<>();

    public static synchronized CreateAnimalService createCreateAnimalService(AnimalFactory animalFactory) {
        if (counter < maxCount) {
            createAnimalService = new CreateAnimalServiceImpl(animalFactory);
            createdServicePrototypes.put(createAnimalService, AnimalType.values()[counter++]);
        }
        return createAnimalService;
    }

    public static synchronized AnimalType currentCreateAnimalServiceAnimalType(CreateAnimalService crAnimalService) {
        return createdServicePrototypes.get(crAnimalService);
    }
}
