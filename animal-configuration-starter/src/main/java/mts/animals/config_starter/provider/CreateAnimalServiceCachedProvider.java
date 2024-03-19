package mts.animals.config_starter.provider;

import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterNullPointerException;
import mts.animals.config_starter.service.CreateAnimalService.CreateAnimalService;
import org.springframework.beans.factory.ObjectProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class CreateAnimalServiceCachedProvider implements CreateAnimalServiceProvider {

    private static final Map<AnimalType, CreateAnimalService> strongCache = new ConcurrentHashMap<>();

    private final ObjectProvider<CreateAnimalService> animalServiceObjectProvider;

    public CreateAnimalServiceCachedProvider(ObjectProvider<CreateAnimalService> animalServiceObjectProvider) {
        if (animalServiceObjectProvider == null) {
            throw new AnimalConfigurationStarterNullPointerException("CreateAnimalServiceCachedProvider:: param " +
                    "animalServiceObjectProvider should not be null");
        }
        this.animalServiceObjectProvider = animalServiceObjectProvider;
    }

    public static Map<AnimalType, CreateAnimalService> getCachedValues() {
        return Collections.unmodifiableMap(strongCache);
    }

    public static Set<AnimalType> getExistingAnimalType() {
        return Collections.unmodifiableSet(strongCache.keySet());
    }

    @Override
    public CreateAnimalService createCreateAnimalService() {
        long count = AnimalTypeCounter.getCount();
        int length = AnimalType.values().length;
        if (count > 0
                && (count >= length)) {

            List<CreateAnimalService> cachedServices = new ArrayList<>(strongCache.values());

            return cachedServices.get(ThreadLocalRandom.current().nextInt(length));
        }

        CreateAnimalService result = animalServiceObjectProvider.getIfAvailable();
        if (Objects.isNull(result)) {
            throw new RuntimeException("Caramba!");
        }

        var animalType = result.getAnimalType();
        if (Objects.nonNull(animalType)) {
            strongCache.put(animalType, result);
        }

        return result;
    }

}
