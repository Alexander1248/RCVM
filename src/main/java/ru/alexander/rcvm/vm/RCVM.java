package ru.alexander.rcvm.vm;


import ru.alexander.rcvm.vm.instructions.d.*;
import ru.alexander.rcvm.vm.instructions.l.*;
import ru.alexander.rcvm.vm.instructions.system.*;
import ru.alexander.rcvm.vm.instructions.transforms.*;

public class RCVM {
    private final RCVMInstruction[] instructions;

    public byte[] memory;

    public byte[] stack;
    public int stackPtr;

    public byte[] code;
    public int codePtr;

    public int state;


    private final RCVMInterface RCVMInterface;

    public RCVM(RCVMInstruction[] instructions) {
        this.instructions = instructions;
        stackPtr = 0;
        codePtr = 0;
        RCVMInterface = new RCVMInterface(this);
    }

    public RCVM() {
        this(new RCVMInstruction[]{
                new End(),
                new JIfT(),
                new Jump(),
                new Mov(),
                new Pull(),
                new Push(),
                new Set(),
                new Put(),
                new Ptr(),

                new LAdd(),
                new LAnd(),
                new LDiv(),
                new LEq(),
                new LLet(),
                new LLs(),
                new LLt(),
                new LMet(),
                new LMt(),
                new LMul(),
                new LNeq(),
                new LNot(),
                new LOr(),
                new LRem(),
                new LRs(),
                new LSub(),
                new LXor(),
                new LAbs(),

                new DAdd(),
                new DCos(),
                new DDiv(),
                new DEq(),
                new DExp(),
                new DLet(),
                new DLog(),
                new DLt(),
                new DMet(),
                new DMt(),
                new DMul(),
                new DNeq(),
                new DPow(),
                new DRem(),
                new DSin(),
                new DSqrt(),
                new DSub(),
                new DAbs(),

                new BToL(),
                new DToF(),
                new DToL(),
                new FToD(),
                new IToL(),
                new LToB(),
                new LToD(),
                new LToI(),
                new LToS(),
                new SToL()
        });
    }
    public void execute() {
        state = 0;
        for (RCVMInstruction instruction : instructions)
            if (instruction.execute(RCVMInterface))
                break;
    }
}
