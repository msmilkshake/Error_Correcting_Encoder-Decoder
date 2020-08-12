package correcter.logic;

import correcter.tool.Tool;

public class Encoder {
    
    //Encodes bytes using BITWISE OPERATIONS.
    public static byte[] bitwiseEncoder(byte[] bytes) {
        int bitCount = bytes.length * 8;
        byte[] encodedBytes = new byte[bitCount / 3 + (bitCount % 3 == 0 ? 0 : 1)];
        int arrayPointer = 0;
        int buildByte = 0;
        short stepCount = 0;
        int xorResult = 0;
    
        for (byte b : bytes) {
            int intByte = b & 0xFF;
            for (int offset = 7; offset >= 0; --offset) {
                int relevantBit = intByte >> offset & 1;
                xorResult ^= relevantBit;
                buildByte <<= 2;
                buildByte |= relevantBit << 1 | relevantBit;
                ++stepCount;
            
                if (stepCount == 3) {
                    buildByte <<= 2;
                    buildByte |= xorResult << 1 | xorResult;
                    encodedBytes[arrayPointer++] = (byte) buildByte;
                    buildByte = 0;
                    stepCount = 0;
                    xorResult = 0;
                }
            }
        }
        
        if (arrayPointer < encodedBytes.length) {
            for (; stepCount > 0; --stepCount) {
                buildByte <<= 2;
            }
            buildByte |= xorResult << 1 | xorResult;
            encodedBytes[arrayPointer] = (byte) buildByte;
        }
        
        return encodedBytes;
    }
    
    //Encodes bytes using STRING MANIPULATION.
    public static byte[] stringManipEncoder(byte[] bytes) {
        int bitCount = bytes.length * 8;
        byte[] encodedBytes = new byte[bitCount / 3 + (bitCount % 3 == 0 ? 0 : 1)];
        int arrayPointer = 0;
        StringBuilder buildByte = new StringBuilder();
        short xorResult = 0;
        
        for (byte b : bytes) {
            String currentByte = Tool.byteToBinaryString(b);
            for (char bit : currentByte.toCharArray()) {
                xorResult += Short.parseShort(String.valueOf(bit));
                buildByte.append(bit).append(bit);
                
                if (buildByte.length() == 6) {
                    xorResult %= 2;
                    buildByte.append(xorResult).append(xorResult);
                    encodedBytes[arrayPointer++] = (byte) Integer
                            .parseInt(buildByte.toString(), 2);
                    buildByte.setLength(0);
                    xorResult = 0;
                }
            }
        }
        
        if (arrayPointer < encodedBytes.length) {
            while (buildByte.length() < 6) {
                buildByte.append("00");
            }
            xorResult %= 2;
            buildByte.append(xorResult).append(xorResult);
            encodedBytes[arrayPointer] = (byte) Integer
                    .parseInt(buildByte.toString(), 2);
        }
        
        return encodedBytes;
    }
}
