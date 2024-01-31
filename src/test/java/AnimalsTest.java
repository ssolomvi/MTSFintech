import org.example.domain.Cat;
import org.example.domain.Dog;
import org.example.domain.Shark;
import org.example.domain.Tiger;
import org.example.domain.abstraction.Animal;
import org.example.service.SearchService;
import org.example.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AnimalsTest {
    @Nested
    class EqualityTests {
        @Test
        @DisplayName("Animals equality")
        public void AnimalsEqualTest() {
            Animal tiger = new Tiger("tiger", "Jhon", BigDecimal.valueOf(15561000).setScale(2, HALF_EVEN),
                    "Happy", LocalDate.of(2002, 11, 13));
            Animal shark = new Shark("shark", "Chris", BigDecimal.valueOf(1778000).setScale(2, HALF_EVEN),
                    "Angry", LocalDate.of(2023, 12, 1));
            assertEquals(true, tiger
                    .equals(/*Shallow copy*/new Tiger(tiger.getBreed(), tiger.getName(), tiger.getCost(), tiger.getCharacter(), tiger.getBirthDate())));
            assertEquals(false, tiger.equals(shark));
        }
    }

    @Nested
    class SearchServiceTests {
        private final Animal[] animals = {
                new Tiger("tiger", "Jhon", BigDecimal.valueOf(15561000).setScale(2, HALF_EVEN),
                        "Happy", LocalDate.of(2002, 11, 13)),
                new Shark("shark", "Chris", BigDecimal.valueOf(1778000).setScale(2, HALF_EVEN),
                        "Angry", LocalDate.of(2023, 1, 2)),
                new Cat("cat", "Shusha", BigDecimal.valueOf(1500).setScale(2, HALF_EVEN),
                        "Purring", LocalDate.of(2020, 10, 16)),
                new Dog("dog", "Arnold", BigDecimal.valueOf(2000).setScale(2, HALF_EVEN),
                        "Loyal", LocalDate.of(2024, 1, 31)),
                new Dog("white-dog", "Masya", BigDecimal.valueOf(1750).setScale(2, HALF_EVEN),
                        "Funny", LocalDate.of(2017, 2, 12)),
                new Cat("cat", "Shusha", BigDecimal.valueOf(1500).setScale(2, HALF_EVEN),
                        "Purring", LocalDate.of(2020, 10, 16))
        };


        @Test
        @DisplayName("Find leap year names")
        public void findLeapYearNamesTest() {
            String[] expectedLeapYearAnimalsNames = {"Shusha", "Arnold", "Shusha"};

            SearchService searchService = new SearchServiceImpl();
            String[] actualLeapYearAnimalsNames = searchService.findLeapYearNames(animals);
            assertArrayEquals(expectedLeapYearAnimalsNames, actualLeapYearAnimalsNames);
        }

        @ParameterizedTest(name = "{index} - find older than {0} year")
        @ValueSource(ints = {1, 2, 4})
        @DisplayName("Find older animals")
        public void findOlderAnimalTest(int param) {
            Animal[] expectedOlderThan;
            if (param == 1) {
                expectedOlderThan = new Animal[]{ animals[0], animals[1], animals[2], animals[4], animals[5] };
            } else if (param == 2) {
                expectedOlderThan = new Animal[]{ animals[0], animals[2], animals[4], animals[5] };
            } else {
                expectedOlderThan = new Animal[]{animals[0], animals[4] };
            }
            SearchService searchService = new SearchServiceImpl();
            Animal[] actualOlderThan = searchService.findOlderAnimal(animals, param);
            assertArrayEquals(expectedOlderThan, actualOlderThan);
        }

        @Test
        @DisplayName("Find duplicate animals")
        public void findDuplicate() {
            SearchService searchService = new SearchServiceImpl();
            Animal[] expectedDuplicates = {animals[5]};

            Animal[] actualDuplicates = searchService.findDuplicate(animals);
            assertArrayEquals(expectedDuplicates, actualDuplicates);
        }
    }
}
