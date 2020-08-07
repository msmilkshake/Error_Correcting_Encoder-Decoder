package correcter;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    private static final Random RND = new Random();
    private static final int[] MASKS = {1, 2, 4, 8, 16, 32, 64, 128};
    
    public static void main(String[] args) {
        File sendFile = new File("send.txt");
        File receivedFile = new File("received.txt");
        char[] fileChars = getCharsFromFile(sendFile);
        byte[] fileBytes = bitwiseErrors(fileChars);
        writeToFile(fileBytes, receivedFile);
    }
    
    // BITWISE ARITHMETICS Error Generator Method
    private static byte[] bitwiseErrors(char[] chars) {
        byte[] bytes = toByteArray(chars);
        IntStream.range(0, bytes.length)
                .forEach(i -> {
                    int mask = ~MASKS[RND.nextInt(MASKS.length)];
                    bytes[i] ^= mask;
                    bytes[i] = (byte) ~bytes[i];
                });
        return bytes;
    }
    
    // STRING MANIPULATION Error Generator Method
    private static byte[] stringManipErrors(char[] chars) {
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; ++i) {
            StringBuilder sb = new StringBuilder(Integer.toBinaryString(chars[i]));
            while (sb.length() < 8) {
                sb.insert(0, '0');
            }
            int n = RND.nextInt(8);
            sb.setCharAt(n, sb.charAt(n) == '0' ? '1' : '0');
            bytes[i] = (byte) Integer.parseInt(sb.toString(), 2);
        }
        return bytes;
    }
    
    private static void writeToFile(byte[] bytes, File file) {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {
            writer.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static char[] getCharsFromFile(File file) {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[] buff = new char[64];
            int numRead = 0;
            while ((numRead = reader.read(buff)) != -1) {
                String readData = String.valueOf(buff, 0, numRead);
                fileData.append(readData);
                buff = new char[64];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData.toString().toCharArray();
    }
    
    private static byte[] toByteArray(char[] chars) {
        byte[] bytes = new byte[chars.length];
        IntStream.range(0, chars.length)
                .forEach(i -> bytes[i] = (byte) chars[i]);
        return bytes;
    }
}
