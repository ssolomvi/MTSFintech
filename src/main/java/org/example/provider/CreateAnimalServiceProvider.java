package org.example.provider;

import org.example.domain.service.CreateAnimalService.CreateAnimalService;

public interface CreateAnimalServiceProvider {

    String NAME = "example_CreateAnimalServiceProvider";

    CreateAnimalService createCreateAnimalService();

}
