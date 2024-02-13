package mts.animals.app;

import mts.animals.app.service.AnimalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Component
public class AnimalsRepositoryScheduler {
    private static final Logger log = LoggerFactory.getLogger(AnimalsRepositoryScheduler.class);


    @Autowired
    private AnimalsRepository animalsRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void init() {
        int n = 10;
        log.info("Animals born in leap years: " + Arrays.toString(animalsRepository.findLeapYearNames()));
        log.info("Animals older than " + n + ": "+ Arrays.toString(animalsRepository.findOlderAnimal(n)));
        log.info("Duplicates: " + animalsRepository.findDuplicate().toString());

//        animalsRepository.printDuplicate(); // need to log, not directly write to console
    }
}
