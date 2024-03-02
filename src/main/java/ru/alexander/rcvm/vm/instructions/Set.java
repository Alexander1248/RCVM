package ru.alexander.rcvm.vm.instructions;

import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Set implements Instruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 1) return false;
        vm.moveCodePtr(1);

        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int from = buffer.getInt();
        int len = buffer.getInt();
        vm.moveCodePtr(8);
        buffer = ByteBuffer.wrap(vm.getCode(len));
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++)
            data[i] = buffer.get();
        vm.moveCodePtr(len);
        vm.setMem(from, data);

        return true;
    }
}
