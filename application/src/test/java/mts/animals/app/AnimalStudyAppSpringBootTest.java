package mts.animals.app;

import mts.animals.configStarter.animals.Cat;
import mts.animals.configStarter.animals.Dog;
import mts.animals.configStarter.animals.Tiger;
import mts.animals.configStarter.bpp.CustomBeanPostProcessor;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.configStarter.abstraction.Animal;
import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.configStarter.provider.AnimalTypeCounter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.math.RoundingMode.HALF_EVEN;
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
        Field field = ReflectionUtils.findField(animalsRepository.getClass(), "animals", List.class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);

        return field;
    }

    @Test
    public void animalsRepositoryIsNotEmpty() {
        List<Animal> animalsRepositoryArray =
                (List<Animal>) Objects.requireNonNull(ReflectionUtils.getField(getAnimalRepositoryField(), animalsRepository));
        assertNotEquals(0, animalsRepositoryArray.size());
    }

    private void setNewListToAnimalRepositoryList(List<Animal> newArray) {
        Field animalRepositoryArray = getAnimalRepositoryField();

        try {
            animalRepositoryArray.set(animalsRepository, newArray);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Carramba trying to set new value to array \"animals\" of AnimalRepository\n" + e);
        }
    }

    @Test
    public void animalsRepositoryFindLeapYearNamesTest() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class), Mockito.mock(Animal.class));
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.of(2001, 1, 1));

        setNewListToAnimalRepositoryList(testAnimals);

        Map<String, LocalDate> leapYearNames = animalsRepository.findLeapYearNames();

        assertEquals(1, leapYearNames.keySet().size());
    }

    @Test
    public void animalsRepositoryFindOlderAnimalTest() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class), Mockito.mock(Animal.class));
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));

        setNewListToAnimalRepositoryList(testAnimals);

        Map<Animal, Integer> olderAnimals = animalsRepository.findOlderAnimal(9);

        assertEquals(1, olderAnimals.keySet().size());
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

    @Test
    public void checkFindOlderAnimalForIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(0));
    }

    @Test
    public void checkFindLeapYearNames() {
        Map<String, LocalDate> actualMap = animalsRepository.findLeapYearNames();
        for (Map.Entry<String, LocalDate> entry : actualMap.entrySet()) {
            assertTrue(entry.getKey().contains(" "));
            assertTrue(entry.getValue().isLeapYear());
        }
    }

    @Test
    public void checkFindOlderAnimal() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class),
                Mockito.mock(Animal.class), Mockito.mock(Animal.class));
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2021, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.of(2022, 12, 1));
        when(testAnimals.get(2).getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));

        setNewListToAnimalRepositoryList(testAnimals);

        Map<Animal, Integer> actualMap = animalsRepository.findOlderAnimal(4);

        assertEquals(1, actualMap.size());
        assertEquals(3, (int) actualMap.entrySet().iterator().next().getValue());
    }

    final List<Animal> testDuplicateAnimals = Arrays.asList(
            new Tiger("tiger", "Jhon", BigDecimal.valueOf(15561000).setScale(2, HALF_EVEN),
                    "Happy", LocalDate.of(2002, 11, 13)),
            new Dog("dog", "Arnold", BigDecimal.valueOf(2000).setScale(2, HALF_EVEN),
                    "Loyal", LocalDate.of(2024, 1, 31)),
            new Dog("dog", "Arnold", BigDecimal.valueOf(2000).setScale(2, HALF_EVEN),
                    "Loyal", LocalDate.of(2024, 1, 31)),
            new Cat("cat", "Shusha", BigDecimal.valueOf(1500).setScale(2, HALF_EVEN),
                    "Purring", LocalDate.of(2020, 10, 16)),
            new Cat("cat", "Shusha", BigDecimal.valueOf(1500).setScale(2, HALF_EVEN),
                    "Purring", LocalDate.of(2020, 10, 16)),
            new Cat("cat", "Shusha", BigDecimal.valueOf(1500).setScale(2, HALF_EVEN),
                    "Purring", LocalDate.of(2020, 10, 16))
    );

    @Test
    public void checkFindDuplicates() {
        setNewListToAnimalRepositoryList(testDuplicateAnimals);

        Map<String, Integer> actualMap = animalsRepository.findDuplicate();

        Iterator<Map.Entry<String, Integer>> iterator = actualMap.entrySet().iterator();
        Map.Entry<String, Integer> firstActualEntry = iterator.next();
        Map.Entry<String, Integer> secondActualEntry = iterator.next();

        Map.Entry<String, Integer> firstExpectedEntry = new AbstractMap.SimpleEntry<>("dog", 1);
        Map.Entry<String, Integer> secondExpectedEntry = new AbstractMap.SimpleEntry<>("cat", 2);

        assertTrue(firstActualEntry.equals(firstExpectedEntry) || firstActualEntry.equals(secondExpectedEntry));
        assertTrue(secondActualEntry.equals(firstExpectedEntry) || secondActualEntry.equals(secondExpectedEntry));
    }
}
