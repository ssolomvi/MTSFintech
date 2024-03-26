package mts.animals.config_starter.config;

import mts.animals.config_starter.bpp.CustomBeanPostProcessor;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterIllegalArgument;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterNullPointerException;
import mts.animals.config_starter.factory.animal.AnimalFactory;
import mts.animals.config_starter.factory.animal.AnimalFactoryImpl;
import mts.animals.config_starter.factory.animal.AnimalSimpleFactory;
import mts.animals.config_starter.provider.CreateAnimalServiceCachedProvider;
import mts.animals.config_starter.provider.CreateAnimalServiceProvider;
import mts.animals.config_starter.service.CreateAnimalService.CreateAnimalService;
import mts.animals.config_starter.service.CreateAnimalService.CreateAnimalServiceImpl;
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
@EnableConfigurationProperties(AnimalConfigurationProperties.class)
public class Configurations {

    private final AnimalConfigurationProperties animalConfigurationProperties;

    public Configurations(@Autowired AnimalConfigurationProperties animalConfigurationProperties) {
        this.animalConfigurationProperties = animalConfigurationProperties;
    }

    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }

    @Bean(name = AnimalFactory.NAME)
    @ConditionalOnMissingBean
    public AnimalFactory createAnimalFactory() {
        Field animalSimpleFactoryAnimalConfigurationProperties =
                ReflectionUtils.findField(AnimalSimpleFactory.class, "animalConfigProperties");

        if (Objects.isNull(animalSimpleFactoryAnimalConfigurationProperties)) {
            throw new AnimalConfigurationStarterNullPointerException("Configurations::createAnimalFactory: Caramba, reflection not help: we don't find the field");
        }

        ReflectionUtils.makeAccessible(animalSimpleFactoryAnimalConfigurationProperties);
        try {
            animalSimpleFactoryAnimalConfigurationProperties.set(null, animalConfigurationProperties);
        } catch (IllegalAccessException e) {
            throw new AnimalConfigurationStarterNullPointerException("Configurations::createAnimalFactory: Caramba, reflection not help: we can't set the field");
        }

        return new AnimalFactoryImpl();
    }

    @Bean(name = CreateAnimalServiceProvider.NAME)
    @ConditionalOnMissingBean
    public CreateAnimalServiceProvider createAnimalServiceProvider(ObjectProvider<CreateAnimalService> animalServiceObjectProvider) {
        if (animalServiceObjectProvider == null) {
            throw new AnimalConfigurationStarterIllegalArgument("Configurations::createAnimalServiceProvider: param " +
                    "animalServiceObjectProvider is null");
        }
        return new CreateAnimalServiceCachedProvider(animalServiceObjectProvider);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = CreateAnimalService.NAME)
    @ConditionalOnMissingBean
    public CreateAnimalService createAnimalService(@Autowired AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

}
