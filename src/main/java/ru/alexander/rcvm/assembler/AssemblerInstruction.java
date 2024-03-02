package ru.alexander.rcvm.assembler;

import java.nio.ByteBuffer;

public interface AssemblerInstruction {
    void startTranslation();
    boolean translate(ByteBuffer out, String line);

    void endTranslation();
}
