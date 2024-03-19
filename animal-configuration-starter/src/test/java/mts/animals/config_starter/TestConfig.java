package mts.animals.config_starter;

import mts.animals.config_starter.config.AnimalConfigurationProperties;
import mts.animals.config_starter.factory.animal.AnimalFactory;
import mts.animals.config_starter.factory.animal.AnimalFactoryImpl;
import mts.animals.config_starter.factory.animal.AnimalSimpleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

@TestConfiguration
public class TestConfig {

    @Autowired
    private AnimalConfigurationProperties animalConfigurationProperties;

    @Bean(name = AnimalFactory.NAME + "Test")
    public AnimalFactory createAnimalFactoryTestBean() {
        Field animalSimpleFactoryAnimalConfigurationProperties =
                ReflectionUtils.findField(AnimalSimpleFactory.class, "animalConfigProperties");

        if (Objects.isNull(animalSimpleFactoryAnimalConfigurationProperties)) {
            throw new RuntimeException("Abrakodabra, reflection not help: we don't find the field");
        }

        ReflectionUtils.makeAccessible(animalSimpleFactoryAnimalConfigurationProperties);
        try {
            animalSimpleFactoryAnimalConfigurationProperties.set(null, animalConfigurationProperties);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Abrakodabra, reflection not help: we can't set the field");
        }

        return new AnimalFactoryImpl();
    }

}
