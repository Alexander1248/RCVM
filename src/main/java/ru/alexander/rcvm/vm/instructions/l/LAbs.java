package ru.alexander.rcvm.vm.instructions.l;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class LAbs implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 60) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int out = buffer.getInt();
        int a = buffer.getInt();
        vm.moveCodePtr(8);

        long aVal = ByteBuffer.wrap(vm.getMem(a, 8)).getLong();
        buffer = ByteBuffer.allocate(8);
        buffer.putLong(Math.abs(aVal));
        vm.setMem(out, buffer.array());

        return true;
    }
}
