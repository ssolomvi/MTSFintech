package mts.animals.app.scheduling.scheduled_tasks;

import mts.animals.app.service.AnimalsRepository;

public class FindAverageAgeLogRunnable extends AnimalsRepositoryLogging implements Runnable {
    public FindAverageAgeLogRunnable(AnimalsRepository repository) {
        super(repository);
    }

    @Override
    public void run() {
        log.info(String.format("%s: %f", getName(), repository.findAverageAge()));
    }
}
