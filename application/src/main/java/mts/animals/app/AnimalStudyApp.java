package mts.animals.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnimalStudyApp {
    public static void main(String[] args) {
        SpringApplication.run(AnimalStudyApp.class, args);
/*
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

        ctx.close();*/
    }

}