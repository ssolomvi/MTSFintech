package mts.animals.app.config;

import mts.animals.app.bpp.CustomBeanPostProcessor;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.app.service.impl.AnimalsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }

    @Bean(name = AnimalsRepository.NAME, initMethod = "postConstruct")
    public AnimalsRepository animalsRepository(@Autowired CreateAnimalServiceProvider createAnimalServiceProvider) {
        return new AnimalsRepositoryImpl(createAnimalServiceProvider);
    }

}
