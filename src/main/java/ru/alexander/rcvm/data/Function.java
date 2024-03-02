package ru.alexander.rcvm.data;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private final Token name;

    private final List<Token> tokens = new ArrayList<>();

    private final List<Token> variables = new ArrayList<>();
    private final List<Token> arguments = new ArrayList<>();

    public Function(Token name) {
        this.name = name;
    }


    public Token name() {
        return name;
    }
    public List<Token> tokens() {
        return tokens;
    }

    public List<Token> vars() {
        return variables;
    }

    public List<Token> args() {
        return arguments;
    }
}
