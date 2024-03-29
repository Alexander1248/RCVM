package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Jump implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 6) return false;
        vm.moveCodePtr(1);
        vm.jumpCodePtr(ByteBuffer.wrap(vm.getCode(4)).getInt());
        return true;
    }
}
