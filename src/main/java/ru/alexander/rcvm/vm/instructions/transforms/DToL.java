package ru.alexander.rcvm.vm.instructions.transforms;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class DToL implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 59) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int out = buffer.getInt();
        int in = buffer.getInt();
        vm.moveCodePtr(8);

        double val = ByteBuffer.wrap(vm.getMem(in, 8)).getDouble();
        buffer = ByteBuffer.allocate(8);
        buffer.putLong((long) val);
        vm.setMem(out, buffer.array());

        return true;
    }
}
