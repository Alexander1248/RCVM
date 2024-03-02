package ru.alexander.rcvm.vm;


public class RCVM {
    private final Instruction[] instructions;

    public byte[] memory;

    public byte[] stack;
    public int stackPtr;

    public byte[] code;
    public int codePtr;

    public int state;


    private final RCVMInterface RCVMInterface;

    public RCVM(Instruction[] instructions) {
        this.instructions = instructions;
        stackPtr = 0;
        codePtr = 0;
        RCVMInterface = new RCVMInterface(this);
    }
    public void execute() {
        state = 0;
        for (Instruction instruction : instructions)
            if (instruction.execute(RCVMInterface))
                break;
    }
}
