package org.example;

import org.example.domain.app.config.AppConfig;
import org.example.service.AnimalsRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        ctx.registerBean(AnimalFactory.NAME, AnimalFactoryImpl.class);
//        ctx.registerBean(AnimalsRepository.NAME, AnimalsRepositoryImpl.class);
//        ctx.registerBean(CreateAnimalService.NAME, CreateAnimalServiceImpl.class);

        AnimalsRepository animalsRepo = ctx.getBean(AnimalsRepository.class);
        animalsRepo.findLeapYearNames();
        animalsRepo.findOlderAnimal(10);
        animalsRepo.findDuplicate();
        animalsRepo.printDuplicate();

        ctx.close();
    }

}