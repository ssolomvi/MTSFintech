package mts.animals.app.config;

import mts.animals.app.bpp.CustomBeanPostProcessor;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.app.service.impl.AnimalsRepositoryImpl;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AppConfigProperties.class)
@Configuration
public class AppConfig {

    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }

    @Bean(name = AnimalsRepository.NAME, initMethod = "postConstruct")
    public AnimalsRepository animalsRepository(@Autowired CreateAnimalServiceProvider createAnimalServiceProvider,
                                               @Autowired AppConfigProperties appConfigProperties) {
        return new AnimalsRepositoryImpl(createAnimalServiceProvider, appConfigProperties);
    }

}
