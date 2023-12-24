package org.example.domain.abstraction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {
    protected LocalDate birthDate;
    protected String breed;
    protected String name;
    protected BigDecimal cost;
    protected String character;

    protected AbstractAnimal(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        this.breed = breed;
        this.name = name;
        this.cost = Objects.isNull(cost) ? null : cost.setScale(2, RoundingMode.HALF_EVEN);
        this.character = character;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                ":{breed=" + getBreed() + ";name=" + getName() +
                ";cost=" + (Objects.isNull(getCost()) ? "null" : getCost().toPlainString()) + ";character=" + getCharacter() +
                ";birthDate=" + birthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ";}";
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

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractAnimal that = (AbstractAnimal) o;
        return Objects.equals(birthDate, that.birthDate)
                && Objects.equals(breed, that.breed)
                && Objects.equals(name, that.name)
                && Objects.equals(cost, that.cost)
                && Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, breed, name, cost, character);
    }
}
