package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class JIfT implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 7) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(5));
        if (buffer.get() != 0)
            vm.jumpCodePtr(buffer.getInt());
        else
            vm.moveCodePtr(5);
        return true;
    }
}
