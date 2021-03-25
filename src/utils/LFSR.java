package utils;

import java.util.BitSet;

public class LFSR {

    private final int[] toXor;

    BitArray register;

    public LFSR(BitArray register, int[] toXor) {
        this.register = register;
        this.toXor = toXor;
    }

    public void iterate() {
        boolean toAdd = xorAll(register);
        register.shiftLeft();
        register.set(0, toAdd);
    }

    private boolean xorAll(BitArray bitArray) {
        boolean lastElement = bitArray.get(toXor[0] - 1);
        for (int i = 1; i < toXor.length; i++) {
            lastElement = lastElement ^ bitArray.get(toXor[i] - 1);
        }
        if (lastElement)
            System.out.println("1");
        else
            System.out.println("0");
        return lastElement;
    }

    public boolean getNext() {
        boolean next = register.get(register.length() - 1);
        iterate();
        return next;
    }

}