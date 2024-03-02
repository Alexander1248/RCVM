package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Mov implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 3) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(12));
        int from = buffer.getInt();
        int to = buffer.getInt();
        int len = buffer.getInt();
        vm.moveCodePtr(12);
        vm.setMem(to, vm.getMem(from, len));

        return true;
    }
}
