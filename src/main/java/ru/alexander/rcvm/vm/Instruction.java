package ru.alexander.rcvm.vm;

public interface Instruction {
    boolean execute(RCVMInterface vm);
}
