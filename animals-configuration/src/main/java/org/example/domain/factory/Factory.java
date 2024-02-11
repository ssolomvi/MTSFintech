package org.example.domain.factory;

import org.example.domain.abstraction.Instance;

public interface Factory {

    //'Factory method'
    Instance createRandomInstance();

}
