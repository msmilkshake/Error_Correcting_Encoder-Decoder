package correcter.logic;

public class HammingEncoder {
    
    public static byte[] bitwiseEncoder(byte[] bytes) {
        byte[] encodedBytes = new byte[bytes.length * 2];
        int arrayPointer = 0;
        int buildByte = 0;
        int buildCount = 0;
        int offset = 0;
        
        for (byte b : bytes) {
            for (short i = 7; i >= 0; --i) {
                int bit = b >>> i & 1;
                buildByte = buildByte << offset | bit;
                offset = offset == 0 ? 2 : 1;
                ++buildCount;
                
                if (buildCount == 4) {
                    buildByte <<= 1;
                    
                    //P1 bit encode
                    int xorBit = 0;
                    for (offset = 1; offset <= 5; offset += 2) {
                        xorBit ^= buildByte >>> offset & 1;
                    }
                    xorBit <<= 7;
                    buildByte |= xorBit;
                    
                    //P2 bit encode
                    xorBit = 0;
                    for (offset = 1; offset <= 5; ++offset) {
                        xorBit ^= buildByte >>> offset & 1;
                        offset = offset == 2 ? 4 : offset;
                    }
                    xorBit <<= 6;
                    buildByte |= xorBit;
                    
                    //P4 bit encode
                    xorBit = 0;
                    for (offset = 1; offset <= 3; ++offset) {
                        xorBit ^= buildByte >>> offset & 1;
                    }
                    xorBit <<= 4;
                    buildByte |= xorBit;
                    
                    //Save byte and reset build vars
                    encodedBytes[arrayPointer++] = (byte) buildByte;
                    buildByte = 0;
                    buildCount = 0;
                    offset = 0;
                }
            }
        }
        
        return encodedBytes;
    }
}
