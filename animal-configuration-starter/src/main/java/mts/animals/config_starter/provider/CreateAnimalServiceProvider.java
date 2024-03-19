package mts.animals.config_starter.provider;

import mts.animals.config_starter.service.CreateAnimalService.CreateAnimalService;

public interface CreateAnimalServiceProvider {

    String NAME = "example_CreateAnimalServiceProvider";

    CreateAnimalService createCreateAnimalService();

}
