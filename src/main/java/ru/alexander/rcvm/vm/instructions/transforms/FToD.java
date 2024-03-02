package ru.alexander.rcvm.vm.instructions.transforms;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class FToD implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 56) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int out = buffer.getInt();
        int in = buffer.getInt();
        vm.moveCodePtr(8);

        float val = ByteBuffer.wrap(vm.getMem(in, 4)).getFloat();
        buffer = ByteBuffer.allocate(8);
        buffer.putDouble(val);
        vm.setMem(out, buffer.array());

        return true;
    }
}
