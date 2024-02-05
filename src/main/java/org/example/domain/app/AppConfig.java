package org.example.domain.app;

import org.example.domain.Cat;
import org.example.factory.animal.AnimalFactory;
import org.example.factory.animal.AnimalFactoryImpl;
import org.example.service.AnimalsRepository;
import org.example.service.CreateAnimalService;
import org.example.service.impl.AnimalsRepositoryImpl;
import org.example.service.impl.CreateAnimalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    public AnimalFactory createAnimalFactory() {
        return new AnimalFactoryImpl();
    }

    @Bean
    @Scope(value = "prototype")
    public CreateAnimalService createAnimalService(@Autowired AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean
    public AnimalsRepository createAnimalsRepository() {
        return new AnimalsRepositoryImpl();
    }
}
