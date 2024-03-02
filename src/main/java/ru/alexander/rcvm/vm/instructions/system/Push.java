package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Push implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 4) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int from = buffer.getInt();
        int len = buffer.getInt();
        vm.moveCodePtr(8);
        vm.push(vm.getMem(from, len));

        return true;
    }
}
