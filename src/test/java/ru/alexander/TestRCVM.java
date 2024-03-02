package ru.alexander;

import org.junit.jupiter.api.Test;
import ru.alexander.rcvm.vm.Instruction;
import ru.alexander.rcvm.vm.RCVM;
import ru.alexander.rcvm.vm.instructions.End;
import ru.alexander.rcvm.vm.instructions.Mov;
import ru.alexander.rcvm.vm.instructions.Set;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TestRCVM {

    @Test
    public void rcvm() {
        Instruction[] instructions = new Instruction[]{
                new End(),
                new Mov(),
                new Set()
        };
        RCVM vm = new RCVM(instructions);
        vm.memory = new byte[128];
        vm.stack = new byte[128];

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 1);
        buffer.putInt(0);
        String text = "Hello world!";
        buffer.putInt(text.length());
        buffer.put(text.getBytes());
        buffer.put((byte) 0);
        buffer.putInt(1);
        vm.code = Arrays.copyOf(buffer.array(), buffer.position());

        RCVMViewer viewer = new RCVMViewer(vm);
        System.out.println();
        viewer.print();

        while (vm.state == 0) {
            vm.execute();

            System.out.println();
            viewer.print();
        }
    }
}
