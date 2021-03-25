package utils;

import java.util.Collections;

public class BitArray {

    boolean[] boolArr;

    public BitArray(int size) {
        this.boolArr = new boolean[size];
    }

    public BitArray(String binary) {
        this.boolArr = new boolean[binary.length()];
        initByStr(binary);
    }

    public BitArray(byte[] bytes) {
        this.boolArr = new boolean[bytes.length * 8];
        initByStr(bytesToBinaryString(bytes));
    }

    private void initByStr(String binary) {
        binary = new StringBuilder(binary).reverse().toString();
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1')
                boolArr[i] = true;
        }
    }

    public static boolean checkBinaryStr(String binary) {
        for (int i = 0; i < binary.length(); i++) {
            switch (binary.charAt(i)) {
                case '1':
                case '0':
                    break;
                default: {
                    return false;
                }
            }
        }
        return true;
    }

    public static byte[] toBytes(String input) {
        //to charArray
        input = new StringBuilder(input).reverse().toString();
        char[] preBitChars = input.toCharArray();
        char[] bitChars = new char[preBitChars.length];
        System.arraycopy(preBitChars, 0, bitChars, 0, preBitChars.length);

        //to bytearray
        byte[] byteArray = new byte[bitChars.length / 8];
        for (int i = 0; i < bitChars.length; i++) {
            if (bitChars[i] == '1') {
                byteArray[byteArray.length - (i / 8) - 1] |= 1 << (i % 8);
            }
        }
        return byteArray;
    }

    public void set(int index, boolean val) {
        boolArr[index] = val;
    }

    public boolean get(int index) {
        return boolArr[index];
    }

    public static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        return result.toString();
    }

    public int length() {
        return boolArr.length;
    }

    @Override
    public String toString() {
        StringBuilder bitRepresentation = new StringBuilder();
        for (boolean b : boolArr) {
            bitRepresentation.append(b ? '1' : '0');
        }
        return bitRepresentation.toString();
    }

    private String bytesToBinaryString(byte[] bytes) {
        StringBuilder binary = new StringBuilder();
        for (byte current : bytes) {
            String byteStr = String.format("%8s", Integer.toBinaryString(current & 0xFF)).replace(' ', '0');
            binary.append(byteStr);
        }

        return binary.toString();
    }

    public void xor(BitArray operand) {
        for (int i = 0; i < Math.min(length(), operand.length()); i++) {
            this.set(i, (operand.get(i) ^ this.get(i)));
        }
    }

    public void shiftLeft() {
        boolean[] resArr = new boolean[length()];
        for (int i = 1; i < length(); i++) {
            if (boolArr[i - 1])
                resArr[i] = true;
        }
        boolArr = resArr;
    }

}