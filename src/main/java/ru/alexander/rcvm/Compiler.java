package ru.alexander.rcvm;

public interface Compiler<I, O> {
    O compile(I code);
}
