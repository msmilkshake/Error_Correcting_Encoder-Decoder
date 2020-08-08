package correcter;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    private static final Random R = new Random();
    private static final int[] MASKS = {1, 2, 4, 8, 16, 32, 64, 128};
    
    public static void main(String[] args) {
        File sendFile = new File("send.txt");
        File receivedFile = new File("received.txt");
        byte[] fileBytes = getFileBytes(sendFile);
        writeToFile(bitwiseErrors(fileBytes), receivedFile);
    }
    
    //Generates errors with bitwise arithmetics
    private static byte[] bitwiseErrors(byte[] bytes) {
        IntStream.range(0, bytes.length)
                .forEach(i -> {
                    int mask = ~MASKS[R.nextInt(MASKS.length)];
                    bytes[i] ^= mask & 0xFF;
                    bytes[i] = (byte) ~bytes[i];
                });
        return bytes;
    }
    
    //Generates errors with string manipulation
    private static byte[] stringManipErrors(byte[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            StringBuilder sb = new StringBuilder(byteToBinaryString(bytes[i]));
            int n = R.nextInt(8);
            sb.setCharAt(n, sb.charAt(n) == '0' ? '1' : '0');
            bytes[i] = (byte) Integer.parseInt(sb.toString(), 2);
        }
        return bytes;
    }
    
    private static String byteToBinaryString(byte b) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(b));
        while (sb.length() < 8) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }
    
    private static void writeToFile(byte[] bytes, File file) {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {
            writer.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static byte[] getFileBytes(File file) {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[] buff = new char[64];
            int numRead = 0;
            while ((numRead = reader.read(buff)) != -1) {
                String readData = String.valueOf(buff, 0, numRead);
                fileData.append(readData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] dataChars = fileData.toString().toCharArray();
        return toByteArray(dataChars);
    }
    
    private static byte[] toByteArray(char[] chars) {
        byte[] bytes = new byte[chars.length];
        IntStream.range(0, chars.length)
                .forEach(i -> bytes[i] = (byte) chars[i]);
        return bytes;
    }
}
