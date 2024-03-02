package ru.alexander.rcvm.vm.instructions.d;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class DRem implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 37) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(12));
        int out = buffer.getInt();
        int a = buffer.getInt();
        int b = buffer.getInt();
        vm.moveCodePtr(12);

        double aVal = ByteBuffer.wrap(vm.getMem(a, 8)).getDouble();
        double bVal = ByteBuffer.wrap(vm.getMem(b, 8)).getDouble();
        buffer = ByteBuffer.allocate(8);
        buffer.putDouble(aVal - bVal * Math.floor(aVal / bVal));
        vm.setMem(out, buffer.array());

        return true;
    }
}
