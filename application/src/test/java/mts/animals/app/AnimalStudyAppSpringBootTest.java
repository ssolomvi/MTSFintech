package mts.animals.app;

import mts.animals.configStarter.bpp.CustomBeanPostProcessor;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.configStarter.provider.AnimalTypeCounter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnimalStudyAppSpringBootTest {

    @Autowired
    private CustomBeanPostProcessor customBeanPostProcessor;

    @Autowired
    private AnimalsRepository animalsRepository;

    // prove that counter increments after creating CreateAnimalService

    private Field getAnimalRepositoryField() {
        Field field = ReflectionUtils.findField(animalsRepository.getClass(), "animals", Animal[].class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);

        return field;
//        return (Animal[]) Objects.requireNonNull(ReflectionUtils.getField(field, animalsRepository));
    }

    @Test
    public void animalsRepositoryIsNotEmpty() {
        Animal[] animalsRepositoryArray =
                (Animal[]) Objects.requireNonNull(ReflectionUtils.getField(getAnimalRepositoryField(), animalsRepository));
        assertNotEquals(0, animalsRepositoryArray.length);
    }

    private void setNewArrayToAnimalRepositoryArray(Animal[] newArray) {
        Field animalRepositoryArray = getAnimalRepositoryField();

        try {
            animalRepositoryArray.set(animalsRepository, newArray);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Carramba trying to set new value to array \"animals\" of AnimalRepository\n" + e);
        }
    }

    @Test
    public void animalsRepositoryFindLeapYearNamesTest() {
        Animal[] testAnimals = {Mockito.mock(Animal.class), Mockito.mock(Animal.class)};
        when(testAnimals[0].getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(testAnimals[1].getBirthDate()).thenReturn(LocalDate.of(2001, 1, 1));

        setNewArrayToAnimalRepositoryArray(testAnimals);

        String[] leapYearNames = animalsRepository.findLeapYearNames();

        assertEquals(1, leapYearNames.length);
    }

    @Test
    public void animalsRepositoryFindOlderAnimalTest() {
        Animal[] testAnimals = {Mockito.mock(Animal.class), Mockito.mock(Animal.class)};
        when(testAnimals[0].getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(testAnimals[1].getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));

        setNewArrayToAnimalRepositoryArray(testAnimals);

        Animal[] olderAnimals = animalsRepository.findOlderAnimal(9);

        assertEquals(1, olderAnimals.length);
    }

    @Test
    public void testPostProcessBeforeInitialization() {
        CustomBeanPostProcessor customBeanPostProcessor = new CustomBeanPostProcessor();
        CreateAnimalService createAnimalService = Mockito.mock(CreateAnimalService.class);
        AnimalTypeCounter.reset();

        customBeanPostProcessor.postProcessBeforeInitialization(createAnimalService, "createAnimalService");

        AnimalType expectedType = AnimalType.values()[0];

        Mockito.verify(createAnimalService).setAnimalType(expectedType);
        assertEquals(1, AnimalTypeCounter.getCount());
    }

}
