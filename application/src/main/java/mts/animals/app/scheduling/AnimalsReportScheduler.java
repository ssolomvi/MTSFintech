package mts.animals.app.scheduling;

import mts.animals.app.config.AppConfigProperties;
import mts.animals.app.service.AnimalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component(AnimalsReportSchedulerMBean.NAME)
public class AnimalsReportScheduler implements AnimalsReportSchedulerMBean {

    private static final Logger log = LoggerFactory.getLogger(AnimalsReportScheduler.class);

    private final AnimalsRepository animalsRepository;

    private final int animalCount;

    @Autowired
    public AnimalsReportScheduler(AnimalsRepository animalsRepository,
                                  AppConfigProperties appConfigProperties) {
        this.animalsRepository = animalsRepository;

        this.animalCount = Optional.ofNullable(appConfigProperties)
                .map(AppConfigProperties::getAnimalCount)
                .orElseThrow();
    }

    @Scheduled(fixedRate = 60_000)
    @Override
    public String executeTask() {
        log.info("Animals born in leap years: {}", Arrays.toString(animalsRepository.findLeapYearNames()));
        log.info("Animals older than {}: {}", animalCount, Arrays.toString(animalsRepository.findOlderAnimal(animalCount)));
        log.info("Duplicates: {}", animalsRepository.findDuplicate());

        animalsRepository.printDuplicate();

        return "See log file :D";
    }

}
