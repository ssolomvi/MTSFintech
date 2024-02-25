package mts.animals.configStarter;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.animals.Cat;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.factory.animal.AnimalFactory;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class StarterTest {

    @SpyBean
    private CreateAnimalServiceProvider createCreateAnimalServiceProvider;

    @Test
    public void createAnimalServiceCreateRandomAnimalsOverload() {
        int n = 10;

        Map<String, List<Animal>> animalsCreatedByOverload;

        CreateAnimalService service = createCreateAnimalServiceProvider.createCreateAnimalService();
        if (service instanceof CreateAnimalServiceImpl) {
            animalsCreatedByOverload = ((CreateAnimalServiceImpl) service)
                    .createRandomAnimals(n);
        } else {
            throw new IllegalArgumentException("createCreateAnimalService is not an instance of CreateAnimalServiceImpl =(");
        }

        List<String> initialAnimalTypes = new ArrayList<>(n);
        Map.Entry<String, List<Animal>> firstEntry = animalsCreatedByOverload.entrySet().iterator().next();
        String firstAnimalBreed = firstEntry.getKey();
        for (int i = 0; i < n; i++) {
            initialAnimalTypes.add(firstAnimalBreed);
        }

        List<String> actualAnimalTypes = new ArrayList<>(n);
        for (Map.Entry<String, List<Animal>> entry : animalsCreatedByOverload.entrySet()) {
            for (Animal entryAnimal: entry.getValue()) {
                actualAnimalTypes.add(entryAnimal.getBreed());
            }
        }

        assertEquals(initialAnimalTypes, actualAnimalTypes);
    }


    // Generates an array of 10 random animals. Expect animals of different types.
    @Test
    public void createAnimalServiceCreateRandomAnimals() {
        CreateAnimalService service = createCreateAnimalServiceProvider.createCreateAnimalService();
        service.setAnimalType(AnimalType.TIGER);

        var randomAnimal = service.createAnimal();

        assertNotEquals("cat", randomAnimal.getBreed());
    }

    private AnimalType getType(CreateAnimalService animalService) {
        Field field = ReflectionUtils.findField(animalService.getClass(), "animalType", AnimalType.class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);

        return (AnimalType) ReflectionUtils.getField(field, animalService);
    }

    @Test
    public void createAnimalServiceProviderCreatesDifferentTypeCreateAnimalService() {
        CreateAnimalService createAnimalServiceFirst = createCreateAnimalServiceProvider.createCreateAnimalService();
        CreateAnimalService createAnimalServiceSecond = createCreateAnimalServiceProvider.createCreateAnimalService();

        AnimalType typeFirst = getType(createAnimalServiceFirst);
        AnimalType typeSecond = getType(createAnimalServiceSecond);

        assertNotEquals(typeFirst, typeSecond);
    }

    // check if createAnimalService.createRandomAnimals() returns the correct map
    @Test
    public void returnValueByCreateAnimalServiceCreateRandomAnimals() {
        CreateAnimalService createAnimalService = createCreateAnimalServiceProvider.createCreateAnimalService();
        Map<String, List<Animal>> actualMap = createAnimalService.createRandomAnimals();

        for (Map.Entry<String, List<Animal>> entry : actualMap.entrySet()) {
            String breed = entry.getKey();
            for (Animal animal : entry.getValue()) {
                assertEquals(animal.getBreed(), breed);
            }
        }
    }

}
