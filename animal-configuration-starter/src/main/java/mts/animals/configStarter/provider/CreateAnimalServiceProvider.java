package mts.animals.configStarter.provider;

import mts.animals.configStarter.service.CreateAnimalService.CreateAnimalService;

public interface CreateAnimalServiceProvider {

    String NAME = "example_CreateAnimalServiceProvider";

    CreateAnimalService createCreateAnimalService();

}
