package mts.animals.app.scheduling.scheduled_tasks;

import mts.animals.app.service.AnimalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AnimalsRepositoryLogging {
    protected AnimalsRepository repository;

    protected static final Logger log = LoggerFactory.getLogger(AnimalsRepositoryLogging.class);

    protected String name = "initial";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected AnimalsRepositoryLogging(@Autowired AnimalsRepository repository) {
        this.repository = repository;
    }
}
