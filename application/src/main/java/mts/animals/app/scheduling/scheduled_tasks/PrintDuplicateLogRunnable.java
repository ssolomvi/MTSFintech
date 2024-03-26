package mts.animals.app.scheduling.scheduled_tasks;

import mts.animals.app.service.AnimalsRepository;
import mts.animals.config_starter.abstraction.Animal;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class PrintDuplicateLogRunnable extends AnimalsRepositoryLogging implements Runnable {
    public PrintDuplicateLogRunnable(AnimalsRepository repository) {
        super(repository);
    }

    private String getToStringDuplicates() {
        Map<String, List<Animal>> duplicates = repository.findDuplicate();
        var entriesJoiner = new StringJoiner("\n");
        for (Map.Entry<String, List<Animal>> entry: duplicates.entrySet()) {
            var entryJoiner = new StringJoiner(" ");
            entryJoiner.add(entry.getKey()).add(" : ");

            var valueJoiner = new StringJoiner(", ");
            for (Animal animal: entry.getValue()) {
                valueJoiner.add(animal.toString());
            }
            entryJoiner.merge(valueJoiner);
            entriesJoiner.merge(entryJoiner);
        }
        return entriesJoiner.toString();
    }

    @Override
    public void run() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(getToStringDuplicates());
        log.info(String.format("%s:\n%s", getName(), joiner));
    }
}
