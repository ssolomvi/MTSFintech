package mts.animals.app.config;

import mts.animals.app.service.AnimalsRepository;
import mts.animals.app.service.impl.AnimalsRepositoryImpl;
import mts.animals.config_starter.provider.CreateAnimalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(AppConfigProperties.class)
@SpringBootConfiguration
public class AppConfig {

    @Bean(name = AnimalsRepository.NAME, initMethod = "postConstruct")
    public AnimalsRepository animalsRepository(@Autowired CreateAnimalServiceProvider createAnimalServiceProvider,
                                               @Autowired AppConfigProperties appConfigProperties) {
        return new AnimalsRepositoryImpl(createAnimalServiceProvider, appConfigProperties);
    }

}
