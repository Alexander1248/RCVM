package ru.alexander.rcvm.vm.instructions.transforms;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class LToI implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 53) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int out = buffer.getInt();
        int in = buffer.getInt();
        vm.moveCodePtr(8);

        long val = ByteBuffer.wrap(vm.getMem(in, 8)).getLong();
        buffer = ByteBuffer.allocate(4);
        buffer.putInt((int) val);
        vm.setMem(out, buffer.array());

        return true;
    }
}
