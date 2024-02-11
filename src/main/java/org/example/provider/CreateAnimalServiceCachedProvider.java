package org.example.provider;

import org.example.domain.enums.AnimalType;
import org.example.domain.service.CreateAnimalService.CreateAnimalService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CreateAnimalServiceCachedProvider implements CreateAnimalServiceProvider {

    private static final Map<AnimalType, CreateAnimalService> strongCache = new ConcurrentHashMap<>();

    private final ObjectProvider<CreateAnimalService> animalServiceObjectProvider;

    public CreateAnimalServiceCachedProvider(ObjectProvider<CreateAnimalService> animalServiceObjectProvider) {
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
        if (count > 0
                && (count >= AnimalType.values().length)) {

            return new HashSet<>(strongCache.values())
                    .iterator()
                    .next();
        }

        CreateAnimalService result = animalServiceObjectProvider.getIfAvailable();
        if (Objects.isNull(result)) {
            throw new RuntimeException("Caramba!");
        }

        Field field = ReflectionUtils.findField(result.getClass(), "animalType", AnimalType.class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);

        AnimalType type = (AnimalType) ReflectionUtils.getField(field, result);

        strongCache.put(type, result);

        return result;
    }

}
