package correcter.logic;

import correcter.tool.Tool;

public class Decoder {
    
    // Decodes bytes using BITWISE OPERATIONS.
    public static byte[] bitwiseDecode(byte[] bytes) {
        int decodedByteLength = bytes.length * 3 / 8;
        byte[] decodedBytes = new byte[decodedByteLength];
        int arrayPointer = 0;
        int buildByte = 0;
        short buildCount = 0;
        
        for (byte b : bytes) {
            int errorPair = 0;
            int parityGroup = b >>> 2;
            
            for (int i = 1; i <= 3; ++i) {
                if ((parityGroup & 1) != (parityGroup >> 1 & 1)) {
                    errorPair = i;
                    break;
                }
                parityGroup >>>= 2;
            }
            
            int correctedBit = 0;
            parityGroup = 0;
            if (errorPair > 0) {
                int wholeByte = b & 0xFF;
                for (int i = 0; i < 4; ++i) {
                    if (i == errorPair) {
                        continue;
                    }
                    correctedBit ^= wholeByte >> (i * 2) & 1;
                }
                wholeByte >>= 2;
                for (int i = 3; i > 0; --i) {
                    parityGroup <<= 2;
                    if (i == errorPair) {
                        parityGroup |= correctedBit << 1 | correctedBit;
                    } else  {
                        parityGroup |= wholeByte >> ((i - 1) * 2) & 0b11;
                    }
                }
            } else {
                parityGroup = b >>> 2 & 0xFF;
            }
            
            for (int j = 2; j >= 0; --j) {
                buildByte = buildByte << 1 | (parityGroup >> (j * 2) & 1);
                ++buildCount;
                if (buildCount == 8) {
                    decodedBytes[arrayPointer++] = (byte) buildByte;
                    buildByte = 0;
                    buildCount = 0;
                }
                if (arrayPointer == decodedByteLength) {
                    break;
                }
            }
            
        }
        return decodedBytes;
    }
    
    // Decodes bytes using STRING MANIPULATION.
    public static byte[] stringManipDecode(byte[] bytes) {
        int decodedByteLength = bytes.length * 3 / 8;
        byte[] decodedBytes = new byte[decodedByteLength];
        int arrayPointer = 0;
        short buildCount = 0;
        StringBuilder buildByte = new StringBuilder();
        for (byte b : bytes) {
            String currentByte = Tool.byteToBinaryString(b);
            int errorPair = -1;
            for (int i = 0; i < 3; ++i) {
                if (currentByte.charAt(i * 2) != currentByte.charAt(i * 2 + 1)) {
                    errorPair = i;
                    break;
                }
            }
            StringBuilder parityGroup = new StringBuilder();
            int bitXorResult = 0;
            if (errorPair != -1) {
                for (int i = 0; i <= 3; ++i) {
                    if (i == errorPair) {
                        continue;
                    }
                    bitXorResult += Integer.parseInt(
                            String.valueOf(currentByte.charAt(i*2)));
                }
                bitXorResult %= 2;
            }
            for (int i = 0; i < 3; ++i) {
                if (i == errorPair) {
                    buildByte.append(bitXorResult == 0 ? '0' : '1');
                } else {
                    buildByte.append(currentByte.charAt(i * 2));
                }
                ++buildCount;
                if (buildCount == 8) {
                    decodedBytes[arrayPointer++] = (byte) Integer.parseInt(
                            buildByte.toString(), 2);
                    buildCount = 0;
                    buildByte.setLength(0);
                }
            }
        }
        return decodedBytes;
    }
}
