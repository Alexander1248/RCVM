package ru.alexander.rcvm.vm.instructions.l;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class LRem implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 20) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(12));
        int out = buffer.getInt();
        int a = buffer.getInt();
        int b = buffer.getInt();
        vm.moveCodePtr(12);

        long aVal = ByteBuffer.wrap(vm.getMem(a, 8)).getLong();
        long bVal = ByteBuffer.wrap(vm.getMem(b, 8)).getLong();
        buffer = ByteBuffer.allocate(8);
        buffer.putLong(aVal % bVal);
        vm.setMem(out, buffer.array());

        return true;
    }
}
