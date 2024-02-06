package org.example.domain.app.config;

import org.example.domain.app.bpp.CustomBeanPostProcessor;
import org.example.factory.animal.AnimalFactory;
import org.example.factory.animal.AnimalFactoryImpl;
import org.example.service.AnimalsRepository;
import org.example.service.CreateAnimalService;
import org.example.service.impl.AnimalsRepositoryImpl;
import org.example.service.impl.CreateAnimalServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }

    @Bean(name = AnimalFactory.NAME)
    public AnimalFactory createAnimalFactory() {
        return new AnimalFactoryImpl();
    }

    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @Bean(name = CreateAnimalService.NAME)
    public CreateAnimalService createAnimalService(@Autowired AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean(name = AnimalsRepository.NAME, initMethod = "postConstruct")
    public AnimalsRepository animalsRepository(@Autowired ObjectProvider<CreateAnimalService> createAnimalServicesBeanProvider) {
        return new AnimalsRepositoryImpl(createAnimalServicesBeanProvider);
    }

}
