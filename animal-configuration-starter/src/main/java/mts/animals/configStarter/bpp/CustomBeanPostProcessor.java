package mts.animals.configStarter.bpp;

import mts.animals.configStarter.enums.AnimalType;
import mts.animals.configStarter.provider.AnimalTypeCounter;
import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(@SuppressWarnings("NullableProblems") Object bean,
                                                  @SuppressWarnings("NullableProblems") String beanName) throws BeansException {
        if (bean instanceof CreateAnimalService) {
            AnimalType type = AnimalType.values()[(int) AnimalTypeCounter.getCount()];

            ((CreateAnimalService) bean).setAnimalType(type);

            AnimalTypeCounter.increment();
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@SuppressWarnings("NullableProblems") Object bean,
                                                 @SuppressWarnings("NullableProblems") String beanName) throws BeansException {
        return bean;
    }

}
