package ru.alexander.rcvm.vm.instructions;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class End implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 0) return false;
        vm.moveCodePtr(1);
        vm.setState(ByteBuffer.wrap(vm.getCode(4)).getInt());
        vm.jumpCodePtr(0);
        return true;
    }
}
