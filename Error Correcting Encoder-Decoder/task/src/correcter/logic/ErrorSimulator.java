package correcter.logic;

import correcter.tool.Tool;

import java.util.Random;
import java.util.stream.IntStream;

public class ErrorSimulator {
    private static final Random R = new Random();
    private static final int[] MASKS = {1, 2, 4, 8, 16, 32, 64, 128};
    
    //Generates errors with bitwise arithmetics
    public static byte[] bitwiseErrors(byte[] bytes) {
        byte[] errorBytes = new byte[bytes.length];
        IntStream.range(0, bytes.length)
                .forEach(i -> {
                    errorBytes[i] = bytes[i];
                    int mask = ~MASKS[R.nextInt(MASKS.length)];
                    errorBytes[i] ^= mask & 0xFF;
                    errorBytes[i] = (byte) ~errorBytes[i];
                });
        return errorBytes;
    }
    
    //Generates errors with string manipulation
    public static byte[] stringManipErrors(byte[] bytes) {
        byte[] errorBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i) {
            StringBuilder sb = new StringBuilder(Tool.byteToBinaryString(bytes[i]));
            int n = R.nextInt(8);
            sb.setCharAt(n, sb.charAt(n) == '0' ? '1' : '0');
            errorBytes[i] = (byte) Integer.parseInt(sb.toString(), 2);
        }
        return errorBytes;
    }
}
