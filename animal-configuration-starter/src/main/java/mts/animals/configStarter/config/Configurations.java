package mts.animals.configStarter.config;

import mts.animals.configStarter.config.AnimalConfigurationProperties;
import mts.animals.configStarter.provider.CreateAnimalServiceCachedProvider;
import mts.animals.configStarter.provider.CreateAnimalServiceProvider;
import mts.animals.configStarter.factory.animal.AnimalFactory;
import mts.animals.configStarter.factory.animal.AnimalFactoryImpl;
import mts.animals.configStarter.factory.animal.AnimalSimpleFactory;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

@Configuration
//@ConditionalOnClass(...)
@EnableConfigurationProperties(AnimalConfigurationProperties.class)
public class Configurations {

    @Autowired
    private AnimalConfigurationProperties animalConfigurationProperties;

    @Bean(name = AnimalFactory.NAME)
    @ConditionalOnMissingBean
    public AnimalFactory createAnimalFactory() {
        Field animalSimpleFactoryAnimalConfigurationProperties =
                ReflectionUtils.findField(AnimalSimpleFactory.class, "animalConfigProperties");

        if (Objects.isNull(animalSimpleFactoryAnimalConfigurationProperties)) {
            throw new RuntimeException("Caramba, reflection not help: we don't find the field");
        }

        ReflectionUtils.makeAccessible(animalSimpleFactoryAnimalConfigurationProperties);
        try {
            animalSimpleFactoryAnimalConfigurationProperties.set(null, animalConfigurationProperties);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Caramba, reflection not help: we can't set the field");
        }

        return new AnimalFactoryImpl();
    }

    @Bean(name = CreateAnimalServiceProvider.NAME)
    @ConditionalOnMissingBean
    public CreateAnimalServiceProvider createAnimalServiceProvider(ObjectProvider<CreateAnimalService> animalServiceObjectProvider) {
        return new CreateAnimalServiceCachedProvider(animalServiceObjectProvider);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = CreateAnimalService.NAME)
    @ConditionalOnMissingBean
    public CreateAnimalService createAnimalService(@Autowired AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

}
