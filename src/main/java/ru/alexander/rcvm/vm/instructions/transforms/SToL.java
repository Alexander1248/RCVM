package ru.alexander.rcvm.vm.instructions.transforms;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class SToL implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 52) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int out = buffer.getInt();
        int in = buffer.getInt();
        vm.moveCodePtr(8);

        short val = ByteBuffer.wrap(vm.getMem(in, 2)).getShort();
        buffer = ByteBuffer.allocate(8);
        buffer.putLong(val);
        vm.setMem(out, buffer.array());

        return true;
    }
}
