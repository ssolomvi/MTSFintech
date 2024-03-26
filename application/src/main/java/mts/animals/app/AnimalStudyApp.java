package mts.animals.app;

import mts.animals.app.scheduling.scheduled_tasks.ScheduledTasks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AnimalStudyApp {

    public static void main(String[] args) {
        SpringApplication.run(AnimalStudyApp.class, args);
    }

}