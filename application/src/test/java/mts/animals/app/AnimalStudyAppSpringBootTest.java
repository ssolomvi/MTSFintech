package mts.animals.app;

import mts.animals.app.exceptions.AppArrayIncorrectLength;
import mts.animals.config_starter.animals.Cat;
import mts.animals.config_starter.animals.Dog;
import mts.animals.config_starter.animals.Tiger;
import mts.animals.config_starter.bpp.CustomBeanPostProcessor;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.enums.AnimalType;
import mts.animals.config_starter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.config_starter.provider.AnimalTypeCounter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AnimalStudyAppSpringBootTest {

    @Autowired
    private CustomBeanPostProcessor customBeanPostProcessor;

    @Autowired
    private AnimalsRepository animalsRepository;

    // prove that counter increments after creating CreateAnimalService

    private Field getAnimalRepositoryField() {
        Field field = ReflectionUtils.findField(animalsRepository.getClass(), "animals", CopyOnWriteArrayList.class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);

        return field;
    }

    @Test
    @DisplayName("animals repository class initialized")
    void animalsRepositoryIsNotEmpty() {
        List<Animal> animalsRepositoryArray =
                (List<Animal>) Objects.requireNonNull(ReflectionUtils.getField(getAnimalRepositoryField(), animalsRepository));
        assertNotEquals(0, animalsRepositoryArray.size());
    }

    private void setNewListToAnimalRepositoryList(CopyOnWriteArrayList<Animal> newArray) {
        Field animalRepositoryArray = getAnimalRepositoryField();

        try {
            animalRepositoryArray.set(animalsRepository, newArray);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Carramba trying to set new value to array \"animals\" of AnimalRepository\n" + e);
        }
    }

    @Test
    @DisplayName("find leap year names correctness")
    void animalsRepositoryFindLeapYearNamesTest() {
        CopyOnWriteArrayList<Animal> testAnimals = new CopyOnWriteArrayList<>(Arrays.asList(Mockito.mock(Animal.class), Mockito.mock(Animal.class)));
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.of(2001, 1, 1));

        setNewListToAnimalRepositoryList(testAnimals);

        Map<String, LocalDate> leapYearNames = animalsRepository.findLeapYearNames();

        assertEquals(1, leapYearNames.keySet().size());
    }

    @Test
    @DisplayName("custom bean post processor before initialization correctness")
    void testPostProcessBeforeInitialization() {
        CustomBeanPostProcessor customBeanPostProcessor = new CustomBeanPostProcessor();
        CreateAnimalService createAnimalService = Mockito.mock(CreateAnimalService.class);
        AnimalTypeCounter.reset();

        customBeanPostProcessor.postProcessBeforeInitialization(createAnimalService, "createAnimalService");

        AnimalType expectedType = AnimalType.values()[0];

        Mockito.verify(createAnimalService).setAnimalType(expectedType);
        assertEquals(1, AnimalTypeCounter.getCount());
    }

    @Test
    @DisplayName("find older animal illegal argument")
    void checkFindOlderAnimalForIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(0));
    }

    @Test
    @DisplayName("find leap year names returns correct map")
    void checkFindLeapYearNames() {
        Map<String, LocalDate> actualMap = animalsRepository.findLeapYearNames();
        for (Map.Entry<String, LocalDate> entry : actualMap.entrySet()) {
            assertTrue(entry.getKey().contains(" "));
            assertTrue(entry.getValue().isLeapYear());
        }
    }

    @Test
    @DisplayName("find older animal correctness")
    void checkFindOlderAnimal() {
        CopyOnWriteArrayList<Animal> testAnimals = new CopyOnWriteArrayList<>(Arrays.asList(Mockito.mock(Animal.class),
                Mockito.mock(Animal.class), Mockito.mock(Animal.class)));
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2021, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.of(2022, 12, 1));
        when(testAnimals.get(2).getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));

        setNewListToAnimalRepositoryList(testAnimals);

        Map<Animal, Integer> actualMap = animalsRepository.findOlderAnimal(4);

        assertEquals(1, actualMap.size());
        assertEquals(3, (int) actualMap.entrySet().iterator().next().getValue());
    }

    final CopyOnWriteArrayList<Animal> testDuplicateAnimals = new CopyOnWriteArrayList<>(Arrays.asList(
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
                    "Purring", LocalDate.of(2020, 10, 16)),
            new Cat("cat", "Plusha", BigDecimal.valueOf(1500).setScale(2, HALF_EVEN),
                    "Purring", LocalDate.of(2020, 10, 16)),
            new Dog("dog", "Garold", BigDecimal.valueOf(2000).setScale(2, HALF_EVEN),
                    "Loyal", LocalDate.of(2024, 1, 31))));

    @Test
    @DisplayName("find duplicates returns correct animals")
    void checkFindDuplicates() {
        setNewListToAnimalRepositoryList(testDuplicateAnimals);

        Map<String, List<Animal>> actualMap = animalsRepository.findDuplicate();

        Iterator<Map.Entry<String, List<Animal>>> iterator = actualMap.entrySet().iterator();
        Map.Entry<String, List<Animal>> firstActualEntry = iterator.next();
        Map.Entry<String, List<Animal>> secondActualEntry = iterator.next();

        Map.Entry<String, List<Animal>> firstExpectedEntry =
                new AbstractMap.SimpleEntry<>("dog", List.of(testDuplicateAnimals.get(1), testDuplicateAnimals.get(2)));
        Map.Entry<String, List<Animal>> secondExpectedEntry =
                new AbstractMap.SimpleEntry<>("cat", List.of(testDuplicateAnimals.get(3), testDuplicateAnimals.get(4), testDuplicateAnimals.get(5)));

        assertTrue(firstActualEntry.equals(firstExpectedEntry) || firstActualEntry.equals(secondExpectedEntry));
        assertTrue(secondActualEntry.equals(firstExpectedEntry) || secondActualEntry.equals(secondExpectedEntry));
    }

    @Test
    @DisplayName("average age calculation correctness")
    void checkFindAverageAge() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class),
                Mockito.mock(Animal.class), Mockito.mock(Animal.class), Mockito.mock(Animal.class), null);
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2021, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.of(2022, 12, 1));
        when(testAnimals.get(2).getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));
        when(testAnimals.get(3).getBirthDate()).thenReturn(null);

        float expectedAverageAge = (float) (5 / 3.0);
        float actualAverageAge = animalsRepository.findAverageAge(testAnimals);

        assertEquals(expectedAverageAge, actualAverageAge);
    }

    @Test
    @DisplayName("find old and expensive correctness")
    void checkFindOldAndExpensive() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class),
                Mockito.mock(Animal.class), Mockito.mock(Animal.class), Mockito.mock(Animal.class), null);
        when(testAnimals.get(0).getBirthDate()).thenReturn(LocalDate.of(2018, 1, 1));
        when(testAnimals.get(1).getBirthDate()).thenReturn(LocalDate.now().minusYears(5));
        when(testAnimals.get(2).getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));
        when(testAnimals.get(3).getBirthDate()).thenReturn(null);

        when(testAnimals.get(0).getCost()).thenReturn(BigDecimal.valueOf(3));
        when(testAnimals.get(1).getCost()).thenReturn(BigDecimal.valueOf(1));
        when(testAnimals.get(2).getCost()).thenReturn(BigDecimal.valueOf(1));

        List<Animal> expected = List.of(testAnimals.get(0));
        List<Animal> actual = animalsRepository.findOldAndExpensive(testAnimals);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("find min cost less than 3 elements")
    void checkFindMinCostAnimalsReturnEmptyList() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class),
                Mockito.mock(Animal.class), Mockito.mock(Animal.class), null);
        when(testAnimals.get(0).getCost()).thenReturn(BigDecimal.valueOf(1));
        when(testAnimals.get(1).getCost()).thenReturn(BigDecimal.valueOf(1));
        when(testAnimals.get(2).getCost()).thenReturn(null);

        List<String> expected = Lists.emptyList();
        List<String> actual = null;
        try {
            actual = animalsRepository.findMinCostAnimals(testAnimals);
        } catch (AppArrayIncorrectLength e) {
            System.out.println(e.getMessage());
        }

        assert actual != null;
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("find min cost correct return list order")
    void findMinCostCorrectReturnListOrder() {
        List<Animal> testAnimals = Arrays.asList(Mockito.mock(Animal.class),
                Mockito.mock(Animal.class), Mockito.mock(Animal.class), Mockito.mock(Animal.class));
        when(testAnimals.get(0).getCost()).thenReturn(BigDecimal.valueOf(1));
        when(testAnimals.get(1).getCost()).thenReturn(BigDecimal.valueOf(1));
        when(testAnimals.get(2).getCost()).thenReturn(BigDecimal.valueOf(3));
        when(testAnimals.get(3).getCost()).thenReturn(BigDecimal.valueOf(1));

        when(testAnimals.get(0).getName()).thenReturn("Bellarose");
        when(testAnimals.get(1).getName()).thenReturn("Tobold");
        when(testAnimals.get(3).getName()).thenReturn("Esmerelda");

        List<String> expected = List.of("Tobold", "Esmerelda", "Bellarose");
        List<String> actual = null;
        try {
            actual = animalsRepository.findMinCostAnimals(testAnimals);
        } catch (AppArrayIncorrectLength e) {
            System.out.println(e.getMessage());
        }

        assert actual != null;
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

}
