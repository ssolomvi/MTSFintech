package org.example.impl;

import org.example.Animal;

import java.math.BigDecimal;

public abstract class AbstractAnimal implements Animal {
    protected String breed;
    protected String name;
    protected BigDecimal cost;
    protected String character;

    @Override
    public String toString() {
        return "animal:{breed=" + breed + ";name=" + name +
                ";cost=" + cost + ";character=" + character + ";}";
    }
}
