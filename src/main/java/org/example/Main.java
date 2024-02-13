package org.example;

import org.example.domain.app.config.AppConfig;
import org.example.provider.CreateAnimalServiceCachedProvider;
import org.example.service.AnimalsRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        System.out.println(CreateAnimalServiceCachedProvider.getExistingAnimalType());
        System.out.println(CreateAnimalServiceCachedProvider.getCachedValues());

        ctx.close();
    }

}