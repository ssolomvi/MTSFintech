package mts.animals.configStarter;

import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//@ComponentScan(basePackageClasses = {"animal-configuration-starter:mts.animals.configStarter.service.CreateAnimalService"})
@SpringBootTest(classes = TestConfig.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class StarterTest {
    @Autowired
    private CreateAnimalServiceProvider createCreateAnimalServiceProvider;

    @Autowired
    private CreateAnimalService createCreateAnimalService;

    @Test
    public void createAnimalServiceCreateRandomAnimalsOverride() {
        int n = 10;

        Animal[] animalsCreatedByOverride;

        if (createCreateAnimalService instanceof CreateAnimalServiceImpl) {
            animalsCreatedByOverride = createCreateAnimalService.createRandomAnimals();
        } else {
            throw new IllegalArgumentException("createCreateAnimalService is not an instance of CreateAnimalServiceImpl =(");
        }

        String[] initialAnimalTypes = new String[n];
        for (int i = 0; i < initialAnimalTypes.length; i++) {
            initialAnimalTypes[i] = animalsCreatedByOverride[0].getBreed();
        }

        String[] actualAnimalTypes = new String[n];
        for (int i = 0; i < animalsCreatedByOverride.length; i++) {
            actualAnimalTypes[i] = animalsCreatedByOverride[i].getBreed();
        }

        assertArrayEquals(initialAnimalTypes, actualAnimalTypes);
    }

    @Test
    public void createAnimalServiceCreateRandomAnimalsOverload() {
        int n = 10;

        Animal[] animalsCreatedByOverride;

        if (createCreateAnimalService instanceof CreateAnimalServiceImpl) {
            animalsCreatedByOverride = ((CreateAnimalServiceImpl)createCreateAnimalService)
                    .createRandomAnimals(n);
        } else {
            throw new IllegalArgumentException("createCreateAnimalService is not an instance of CreateAnimalServiceImpl =(");
        }

        String[] initialAnimalTypes = new String[n];
        for (int i = 0; i < initialAnimalTypes.length; i++) {
            initialAnimalTypes[i] = animalsCreatedByOverride[0].getBreed();
        }

        String[] actualAnimalTypes = new String[n];
        for (int i = 0; i < animalsCreatedByOverride.length; i++) {
            actualAnimalTypes[i] = animalsCreatedByOverride[i].getBreed();
        }

        assertArrayEquals(initialAnimalTypes, actualAnimalTypes);
    }


    // Generates an array of 10 random animals. Expect animals of different types.
    @Test
    public void createAnimalServiceCreateRandomAnimals() {
        createCreateAnimalService.setAnimalType(AnimalType.TIGER);
        Animal randomAnimal = createCreateAnimalService.createAnimal();
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

    // it might be another test to check for animals names
    // (they were brought from application-test.properties, not application.properties)
}
