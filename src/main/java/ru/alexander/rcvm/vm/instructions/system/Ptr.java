package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Ptr implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 2) return false;
        vm.moveCodePtr(1);

        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(4));
        int from = buffer.getInt();
        vm.moveCodePtr(4);
        buffer = ByteBuffer.allocate(4);
        buffer.putInt(vm.getCodePtr());
        vm.setMem(from, buffer.array());

        return true;
    }
}
