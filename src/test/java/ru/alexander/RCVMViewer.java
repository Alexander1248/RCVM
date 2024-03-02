package ru.alexander;

import ru.alexander.rcvm.vm.RCVM;
import ru.alexander.rcvm.vm.RCVMInterface;


public class RCVMViewer {
    private final RCVM rcvm;
    private final RCVMInterface vmi;
    public RCVMViewer(RCVM rcvm) {
        this.rcvm = rcvm;
        vmi = new RCVMInterface(rcvm);
    }
    private void print(byte[] arr, int highlight) {
        for (int i = 0; i < arr.length; i++) {
            String str = Integer.toHexString(arr[i]);
            str = "0".repeat(2 - str.length()) + str;
            if (i == highlight)
                System.out.print("\u001B[32m" + str + " \u001B[0m");
            else System.out.print(str + " ");
            if (i % 16 == 15) System.out.println();
        }
        System.out.println();
    }
    public void printMem() {
        print(rcvm.memory, -1);
    }
    public void printStack() {
        print(rcvm.stack, rcvm.stackPtr);
    }
    public void printCode() {
        print(rcvm.code, rcvm.codePtr);
    }

    public void print() {
        System.out.println("Memory:");
        printMem();
        System.out.println("Stack:");
        printStack();
        System.out.println("Code:");
        printCode();
    }
}
