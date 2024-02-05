package org.example.domain.app;

import org.example.service.CreateAnimalService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CreateAnimalService) {
            CreateAnimalService createAnimalService = (CreateAnimalService) bean;
            createAnimalService.setType();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
//            throws BeansException {
//        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("createAnimalService");
//        beanDefinition.setBeanClassName("org.example.service.impl.CreateAnimalServiceImpl");
//        beanDefinition.setInitMethodName("setType");
//    }

}
