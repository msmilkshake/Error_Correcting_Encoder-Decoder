package correcter.logic;

import correcter.tool.Tool;

public class Decoder {
    
    //Decodes an array of bytes using bitwise arithmetics.
    public static byte[] decode(byte[] bytes) {
        int decodedByteLength = bytes.length * 3 / 8;
        byte[] decodedBytes = new byte[decodedByteLength];
        int buildByte = 0;
        int arrPointer = 0;
        short buildCount = 0;
        
        for (byte b : bytes) {
            int diffPair = 0;
            int parityGroup = b >> 2 & 0xFF;
            
            for (int j = 1; j <= 3; ++j) {
                if ((parityGroup & 1) != (parityGroup >> 1 & 1)) {
                    diffPair = j;
                    break;
                }
                parityGroup >>= 2;
            }
            
            int correctedBit = 0;
            parityGroup = 0;
            if (diffPair > 0) {
                int wholeByte = b & 0xFF;
                for (int j = 0; j < 4; ++j) {
                    if (j == diffPair) {
                        continue;
                    }
                    correctedBit ^= wholeByte >> (j * 2) & 1;
                }
                wholeByte >>= 2;
                for (int j = 3; j > 0; --j) {
                    parityGroup <<= 2;
                    if (j == diffPair) {
                        parityGroup |= correctedBit << 1 | correctedBit;
                    } else  {
                        parityGroup |= wholeByte >> ((j - 1) * 2) & 0b11;
                    }
                }
            } else {
                parityGroup = b >> 2 & 0xFF;
            }
            
            for (int j = 2; j >= 0; --j) {
                buildByte = buildByte << 1 | (parityGroup >> (j * 2) & 1);
                ++buildCount;
                if (buildCount == 8) {
                    decodedBytes[arrPointer++] = (byte) buildByte;
                    buildByte = 0;
                    buildCount = 0;
                }
                if (arrPointer == decodedByteLength) {
                    break;
                }
            }
            
        }
        return decodedBytes;
    }
}
