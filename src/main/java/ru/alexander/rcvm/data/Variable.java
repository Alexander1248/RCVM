package ru.alexander.rcvm.data;

import java.util.Objects;

public class Variable {
    private String name;
    private int startIndex;
    public int endIndex;

    public Variable(String name, int startIndex) {
        this.name = name;
        this.startIndex = startIndex;
        endIndex = startIndex;
    }

    public String getName() {
        return name;
    }

    public int getStartIndex() {
        return startIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
