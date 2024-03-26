package mts.animals.app.scheduling;

import mts.animals.app.config.AppConfigProperties;
import mts.animals.app.exceptions.AppIllegalArgumentException;
import mts.animals.app.exceptions.AppNullPointerException;
import mts.animals.app.scheduling.scheduled_tasks.ScheduledTasks;
import mts.animals.app.service.AnimalsRepository;
import mts.animals.config_starter.abstraction.Animal;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterIllegalArgument;
import mts.animals.config_starter.exceptions.AnimalConfigurationStarterNullPointerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

@Component(AnimalsReportSchedulerMBean.NAME)
public class AnimalsReportScheduler implements AnimalsReportSchedulerMBean {

    private static final Logger log = LoggerFactory.getLogger(AnimalsReportScheduler.class);

    private final AnimalsRepository animalsRepository;

    private final ScheduledTasks tasks;

    private final int animalCount;

    @Autowired
    public AnimalsReportScheduler(AnimalsRepository animalsRepository,
                                  AppConfigProperties appConfigProperties,
                                  @Autowired ScheduledTasks tasks) {
        this.animalsRepository = animalsRepository;

        this.tasks = tasks;

        this.animalCount = Optional.ofNullable(appConfigProperties)
                .map(AppConfigProperties::getAnimalCount)
                .orElseThrow();
    }

    private String getToStringAnimalsBornInLeapYears() {
        Map<String, LocalDate> leapYearAnimals = animalsRepository.findLeapYearNames();
        var builder = new StringBuilder();
        for (Map.Entry<String, LocalDate> entry : leapYearAnimals.entrySet()) {
            builder.append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append(";\n");
        }

        return builder.toString();
    }

    private String getToStringOlderAnimals() {
        Map<Animal, Integer> olderThanAnimals = animalsRepository.findOlderAnimal(animalCount);
        var builder = new StringBuilder();
        for (Map.Entry<Animal, Integer> entry : olderThanAnimals.entrySet()) {
            builder.append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append(";\n");
        }

        return builder.toString();
    }

    @Scheduled(fixedRate = 60_000)
    @Override
    public String executeTask() {
        /*
        var joiner = new StringJoiner("\n");
        try {
            joiner.add("Animals born in leap years: " + getToStringAnimalsBornInLeapYears());
            joiner.add(String.format("Animals older than %d: %s", animalCount, getToStringOlderAnimals()));
            joiner.add("Duplicates: " + animalsRepository.findDuplicate());
            log.info(joiner.toString());
        } catch (AppIllegalArgumentException | AppNullPointerException
                | AnimalConfigurationStarterIllegalArgument | AnimalConfigurationStarterNullPointerException e) {
            log.info(e.getMessage());
        }

        animalsRepository.printDuplicate();
*/
        return "See log file :D";
    }

}
