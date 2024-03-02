package ru.alexander.rcvm.vm.instructions.d;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class DSqrt implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 39) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int out = buffer.getInt();
        int a = buffer.getInt();
        vm.moveCodePtr(8);

        double aVal = ByteBuffer.wrap(vm.getMem(a, 8)).getDouble();
        buffer = ByteBuffer.allocate(8);
        buffer.putDouble(Math.sqrt(aVal));
        vm.setMem(out, buffer.array());

        return true;
    }
}
