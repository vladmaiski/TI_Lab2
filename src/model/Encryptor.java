package model;
import utils.BitArray;
import utils.LFSR;

public class Encryptor {

    private static final int[] toXor = {27, 8, 7, 1};

    public BitArray encrypt(BitArray key, BitArray text) {
        BitArray generatedKey = generateKey(key, text.length());
        generatedKey.xor(text);
        return generatedKey;
    }

    private BitArray generateKey(BitArray key, int requiredLen) {
        LFSR register = new LFSR(key, toXor);
        BitArray generatedKey = new BitArray(requiredLen);
        for (int i = requiredLen - 1; i >= 0; i--) {
            generatedKey.set(i, register.getNext());
        }
        return generatedKey;
    }

    public static int getKeySize() {
        return toXor[0];
    }
}