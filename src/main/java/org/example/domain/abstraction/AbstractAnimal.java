package org.example.domain.abstraction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {

    protected String breed;
    protected String name;
    protected BigDecimal cost;
    protected String character;

    protected AbstractAnimal(String breed, String name, BigDecimal cost, String character) {
        this.breed = breed;
        this.name = name;
        this.cost = Objects.isNull(cost) ? null : cost.setScale(2, RoundingMode.HALF_EVEN);
        this.character = character;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                ":{breed=" + getBreed() + ";name=" + getName() +
                ";cost=" + (Objects.isNull(getCost()) ? "null" : getCost().toPlainString()) + ";character=" + getCharacter() + ";}";
    }

    @Override
    public String getBreed() {
        return breed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String getCharacter() {
        return character;
    }

}
