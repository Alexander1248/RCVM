package ru.alexander.rcvm.vm.instructions.system;

import ru.alexander.rcvm.vm.RCVMInstruction;
import ru.alexander.rcvm.vm.RCVMInterface;

import java.nio.ByteBuffer;

public class Put implements RCVMInstruction {
    @Override
    public boolean execute(RCVMInterface vm) {
        if (Byte.toUnsignedInt(vm.getCode()) != 2) return false;
        vm.moveCodePtr(1);

        ByteBuffer buffer = ByteBuffer.wrap(vm.getCode(8));
        int pos = buffer.getInt();
        int len = buffer.getInt();
        vm.moveCodePtr(8);
        int from = ByteBuffer.wrap(vm.getMem(pos, 4)).getInt();
        buffer = ByteBuffer.wrap(vm.getCode(len));
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++)
            data[i] = buffer.get();
        vm.moveCodePtr(len);
        vm.setMem(from, data);

        return true;
    }
}
