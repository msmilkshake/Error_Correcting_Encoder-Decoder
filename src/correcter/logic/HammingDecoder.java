package correcter.logic;

public class HammingDecoder {
    
    public static byte[] bitwiseDecode(byte[] bytes) {
        byte[] decodedBytes = new byte[bytes.length / 2];
        int arrayPointer = 0;
        int buildByte = 0;
        short buildCount = 0;
        
        for (byte b : bytes) {
            //Check P1 ( ? . X . X . X . )
            int checkBit = b >>> 7 & 1;
            int xorResult = 0;
            int errorOffset = 0;
            for (int offset = 1; offset <= 5; offset += 2) {
                xorResult ^= b >>> offset & 1;
            }
            if (xorResult != checkBit) {
                errorOffset += 1;
            }
            
            //Check P2 ( . ? X . . X X . )
            checkBit = b >>> 6 & 1;
            xorResult = 0;
            for (int offset = 1; offset <= 5; ++offset) {
                xorResult ^= b >>> offset & 1;
                offset = offset == 2 ? 4 : offset;
            }
            if (xorResult != checkBit) {
                errorOffset += 2;
            }
            
            //Check P4 ( . . . ? X X X . )
            checkBit = b >>> 4 & 1;
            xorResult = 0;
            for (int offset = 1; offset <= 3; ++offset) {
                xorResult ^= b >>> offset & 1;
            }
            if (xorResult != checkBit) {
                errorOffset += 4;
            }
            
            //Get correct byte
            int correctMask = ~(1 << 8 - errorOffset);
            int correctByte = ~(b ^ correctMask) & 0xFF;
            
            //Extract relevant bits to buildByte ( . . X . X X X . )
            for (int offset = 5; offset > 0; --offset) {
                buildByte |= correctByte >>> offset & 1;
                ++buildCount;
                if (buildCount == 8) {
                    decodedBytes[arrayPointer++] = (byte) buildByte;
                    buildByte = 0;
                    buildCount = 0;
                } else {
                    buildByte <<= 1;
                }
                if (offset == 5) {
                    --offset;
                }
            }
            
        }
        return decodedBytes;
    }
}
