package correcter.logic;

public class Encoder {
    
    //Encodes an array of bytes using bitwise arithmetics.
    public static byte[] bitwiseEncoder(byte[] bytes) {
        int bitCount = bytes.length * 8;
        byte[] encodedBytes = new byte[bitCount / 3 + (bitCount % 3 == 0 ? 0 : 1)];
        
        int arrayPointer = 0;
        int newByte = 0;
        short stepCount = 0;
        int xorResult = 0;
    
        for (byte b : bytes) {
            int intByte = b & 0xFF;
            for (int offset = 7; offset >= 0; --offset) {
            
                int relevantBit = intByte >> offset & 1;
                xorResult ^= relevantBit;
                newByte <<= 2;
                newByte |= relevantBit << 1 | relevantBit;
                ++stepCount;
            
                if (stepCount == 3) {
                    newByte <<= 2;
                    newByte |= xorResult << 1 | xorResult;
                    encodedBytes[arrayPointer++] = (byte) newByte;
                    newByte = 0;
                    stepCount = 0;
                    xorResult = 0;
                }
            }
        }
        
        if (arrayPointer < encodedBytes.length) {
            for (; stepCount > 0; --stepCount) {
                newByte <<= 2;
            }
            newByte |= xorResult << 1 | xorResult;
            encodedBytes[arrayPointer] = (byte) newByte;
        }
        
        return encodedBytes;
    }
}
