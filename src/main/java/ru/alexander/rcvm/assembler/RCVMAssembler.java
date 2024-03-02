package ru.alexander.rcvm.assembler;

import ru.alexander.rcvm.Compiler;

import java.nio.ByteBuffer;
import java.util.*;

public class RCVMAssembler implements Compiler<String, byte[]> {

    private final AssemblerInstruction[] instructions;

    public RCVMAssembler(AssemblerInstruction[] instructions) {
        this.instructions = instructions;
    }

    @Override
    public byte[] compile(String code) {
        try {
            String[] lines = code.toLowerCase().split("\n");
            ByteBuffer compiled = ByteBuffer.allocate(code.length());

            for (AssemblerInstruction instruction : instructions)
                instruction.startTranslation();

            for (int i = 0; i < lines.length; i++) {
                int cIndex = lines[i].indexOf("//");
                if (cIndex == -1) cIndex = lines[i].length();
                String frag = lines[i].substring(0, cIndex);
                if (frag.isEmpty()) continue;

                for (AssemblerInstruction instruction : instructions)
                    if (instruction.translate(compiled, lines[i])) break;
            }

            for (AssemblerInstruction instruction : instructions)
                instruction.endTranslation();

            return Arrays.copyOf(compiled.array(), compiled.position());
        } catch (IndexOutOfBoundsException | IllegalStateException ex) {
            throw new RuntimeException(ex);
        }
    }
    private static int indexer = 0;
    private int getVar(Map<String, Integer> variables, String name) {
        if(!variables.containsKey(name)) {
            variables.put(name, indexer);
            do {
                indexer++;
            } while (variables.containsValue(indexer));
            if (indexer == Integer.MAX_VALUE) indexer = 0;
        }
        return variables.get(name);
    }
}
