package ru.alexander.rcvm.vm;

import ru.alexander.rcvm.exceptions.RCVMException;

import java.util.Arrays;

public class RCVMInterface {
    private final RCVM vm;

    public RCVMInterface(RCVM vm) {
        this.vm = vm;
    }

    // Memory operations
    public void setMem(int pos, byte[] value) {
        if (vm.memory == null)
            throw new RCVMException("Out of memory");

        if (value.length + pos > vm.memory.length)
            throw new RCVMException("Out of memory");

        System.arraycopy(value, 0, vm.memory, pos, value.length);
    }
    public byte[] getMem(int pos, int length) {
        if (vm.memory == null)
            throw new RCVMException("Out of memory");

        if (length + pos > vm.memory.length)
            throw new RCVMException("Out of memory");

        return Arrays.copyOfRange(vm.memory, pos, pos + length);
    }


    // Stack operations
    public void push(byte[] value) {
        if (vm.stack == null)
            throw new RCVMException("Stack overflow");

        if (value.length + vm.stackPtr > vm.stack.length)
            throw new RCVMException("Stack overflow");

        System.arraycopy(value, 0, vm.stack, vm.stackPtr, value.length);
        vm.stackPtr += value.length;
    }
    public byte[] poll(int length) {
        if (vm.stack == null)
            throw new RCVMException("Stack overflow");

        if (length + vm.stackPtr > vm.memory.length)
            throw new RCVMException("Stack overflow");

        vm.stackPtr -= length;
        return Arrays.copyOfRange(vm.stack, vm.stackPtr, vm.stackPtr + length);
    }

    // Code operations
    public void moveCodePtr(int shift) {
        vm.codePtr += shift;
    }
    public void jumpCodePtr(int pointer) {
        vm.codePtr = pointer;
    }

    public byte[] getCode(int length) {
        if (vm.code == null)
            throw new RCVMException("Code out of bounds");

        if (length + vm.codePtr > vm.code.length)
            throw new RCVMException("Code out of bounds");

        return Arrays.copyOfRange(vm.code, vm.codePtr, vm.codePtr + length);
    }
    public byte getCode() {
        if (vm.code == null)
            throw new RCVMException("Code out of bounds");

        if (vm.codePtr + 1 > vm.code.length)
            throw new RCVMException("Code out of bounds");

        return vm.code[vm.codePtr];
    }

    public void setState(int state) {
        vm.state = state;
    }
}
