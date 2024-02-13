package org.example.factory;

import org.example.domain.abstraction.Instance;

public interface Factory {

    //'Factory method'
    Instance createRandomInstance();

}
