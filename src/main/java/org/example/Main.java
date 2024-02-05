package org.example;

import org.example.domain.app.AppConfig;
import org.example.domain.app.MyBeanPostProcessor;
import org.example.factory.animal.AnimalFactoryImpl;
import org.example.service.AnimalsRepository;
import org.example.service.CreateAnimalService;
import org.example.service.impl.AnimalsRepositoryImpl;
import org.example.service.impl.CreateAnimalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

@ComponentScan("org")
//@Import({AppConfig.class, MyBeanPostProcessor.class})
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CreateAnimalService createAnimalServiceBean = context.getBean(CreateAnimalService.class);
        AnimalsRepository animalsRepositoryBean = context.getBean(AnimalsRepository.class);

        createAnimalServiceBean.createRandomAnimals();

        context.close();
//        AnnotationConfigApplicationContext context = new GenericApplicationContext();
//        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl(new AnimalFactoryImpl());
//        AnimalsRepository animalsRepository = new AnimalsRepositoryImpl();
//
//        context.registerBean(CreateAnimalService.class, () -> createAnimalService);
//        context.registerBean(AnimalsRepository.class, () -> animalsRepository);
//
////        context.getBean(CreateAnimalService.class).createRandomAnimals();
////        context.getBean();
//        context.close();

/*
        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl(new AnimalFactoryImpl());
        SearchService searchService = new SearchServiceImpl();

        Animal[] resultDefaultMethod = new CreateAnimalService() {
        }.createRandomAnimals();
        System.out.println("Names of animals born in leap years:");

        String[] animalsNames = searchService.findLeapYearNames(resultDefaultMethod);
        System.out.println(Arrays.toString(animalsNames));

        Animal[] resultOverrideMethod = createAnimalService.createRandomAnimals();
        int n = 3;
        System.out.println("Animals which are older than " + 3 + " years:");
        Animal[] animalsOlderN = searchService.findOlderAnimal(resultOverrideMethod, n);
        System.out.println(Arrays.toString(animalsOlderN));

        if (createAnimalService instanceof CreateAnimalServiceImpl) {
            Animal[] resultOverloadMethod =
                    ((CreateAnimalServiceImpl) createAnimalService).createRandomAnimals(100);

            System.out.println("Print 'resultOverloadMethod' result");
            System.out.println(Arrays.toString(resultOverloadMethod));
            System.out.println("Duplicate animals:");
            searchService.findDuplicate(resultOverloadMethod);


        }
*/
    }

}