package model;
import utils.BitArray;
import utils.LFSR;

public class Encryptor {

    private static final int[] toXor = {27, 8, 7, 1};
    //private static final int[] toXor = {37, 12, 10, 2};

    public BitArray encrypt(BitArray text, BitArray generatedKey) {
        //BitArray generatedKey = generateKey(key, text.length());
        generatedKey.xor(text);
        return generatedKey;
    }

    public static BitArray generateKey(BitArray key, int requiredLen) {
        LFSR register = new LFSR(key, toXor);
        BitArray generatedKey = new BitArray(requiredLen);
        for (int i = requiredLen - 1; i >= 0; i--) {
            generatedKey.set(i, register.getNext());
        }
        System.out.println(generatedKey.toString());
        return generatedKey;
    }

    public static int getKeySize() {
        return toXor[0];
    }
}