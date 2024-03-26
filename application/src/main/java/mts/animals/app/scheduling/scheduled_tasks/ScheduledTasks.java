package mts.animals.app.scheduling.scheduled_tasks;

import mts.animals.app.service.AnimalsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledTasks {
    public final static String NAME = "example_ScheduledTasks";

    ScheduledExecutorService scheduler;

    private boolean initialized = false;

    private final AnimalsRepository repository;

    public ScheduledTasks(@Autowired AnimalsRepository repository) {
        this.repository = repository;
    }

    // todo: how to assign ScheduledTasks to watch after a specific AnimalsRepository?

    @PostConstruct
    public void postConstruct() {
        if (!initialized) {
            scheduler = new ScheduledThreadPoolExecutor(2);

            PrintDuplicateLogRunnable printDuplicateLogRunnable = new PrintDuplicateLogRunnable(repository);
            FindAverageAgeLogRunnable findAverageAgeLogRunnable = new FindAverageAgeLogRunnable(repository);

            printDuplicateLogRunnable.setName("Duplicates");
            findAverageAgeLogRunnable.setName("Average animal age");

            scheduler.scheduleWithFixedDelay(printDuplicateLogRunnable, 0, 10, TimeUnit.SECONDS);

            scheduler.scheduleWithFixedDelay(findAverageAgeLogRunnable, 0, 20, TimeUnit.SECONDS);

            initialized = true;
        }
    }
}
