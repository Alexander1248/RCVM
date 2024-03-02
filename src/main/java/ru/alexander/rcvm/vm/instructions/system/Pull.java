package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Pull implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 5) return false;
        vm.moveCodePtr(1);
        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int to = buffer.getInt();
        int len = buffer.getInt();
        vm.moveCodePtr(8);
        vm.setMem(to, vm.pull(len));

        return true;
    }
}
