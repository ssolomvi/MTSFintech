package org.example.domain.app.config;

import org.example.domain.app.bpp.CustomBeanPostProcessor;
import org.example.domain.factory.animal.AnimalFactory;
import org.example.provider.CreateAnimalServiceCachedProvider;
import org.example.provider.CreateAnimalServiceProvider;
import org.example.service.AnimalsRepository;
import org.example.domain.service.CreateAnimalService.CreateAnimalService;
import org.example.domain.service.CreateAnimalService.CreateAnimalServiceImpl;
import org.example.service.impl.AnimalsRepositoryImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }

//    @Bean(name = AnimalFactory.NAME)
//    public AnimalFactory createAnimalFactory() {
//        return new AnimalFactoryImpl();
//    }

    @Bean(name = CreateAnimalServiceProvider.NAME)
    public CreateAnimalServiceProvider createAnimalServiceProvider(ObjectProvider<CreateAnimalService> animalServiceObjectProvider) {
        return new CreateAnimalServiceCachedProvider(animalServiceObjectProvider);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = CreateAnimalService.NAME)
    public CreateAnimalService createAnimalService(@Autowired AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean(name = AnimalsRepository.NAME, initMethod = "postConstruct")
    public AnimalsRepository animalsRepository(@Autowired CreateAnimalServiceProvider createAnimalServiceProvider) {
        return new AnimalsRepositoryImpl(createAnimalServiceProvider);
    }

}
