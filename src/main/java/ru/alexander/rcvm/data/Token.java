package ru.alexander.rcvm.data;

import java.util.Objects;

public final class Token {
    public String name;
    private final TokenType type;

    public Token(String name, TokenType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " " + type.name();
    }

    public TokenType type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Token) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }


    public enum TokenType {
        NUMBER,
        VARIABLE,
        FUNCTION,
        MATH,
        INSTRUCTION,
        GROUP_DIVIDER,
        BLOCK_DIVIDER,
        CODE_DIVIDER,
        COMMA_DIVIDER,
        INDEX_DIVIDER,
        GOTO_MARK,
        COMMENT
    }
}
