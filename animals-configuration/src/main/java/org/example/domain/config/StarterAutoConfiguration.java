package org.example.domain.config;

import org.example.domain.AnimalsProperties;
import org.example.domain.abstraction.Animal;
import org.example.domain.factory.animal.AnimalFactory;
import org.example.domain.factory.animal.AnimalFactoryImpl;
import org.example.domain.service.CreateAnimalService.CreateAnimalService;
import org.example.domain.service.CreateAnimalService.CreateAnimalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AnimalFactory.class)
// todo: delete?
@EnableConfigurationProperties(AnimalsProperties.class)
public class StarterAutoConfiguration {
    // todo: delete?
    @Autowired
    private AnimalsProperties animalsProperties;

    @Bean(name = AnimalFactory.NAME)
    @ConditionalOnMissingBean
    public AnimalFactory createAnimalFactory() {
        return new AnimalFactoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateAnimalService createCreateAnimalService(@Autowired AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public Animal createRandomAnimal(@Autowired AnimalFactory animalFactory) {
        return animalFactory.createRandomAnimal();
    }
}
