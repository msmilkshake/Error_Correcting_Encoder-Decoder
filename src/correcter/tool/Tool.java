package correcter.tool;

import java.io.*;
import java.util.stream.IntStream;

public class Tool {
    
    public static byte[] readFileCharsAsBytes(File file) {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {
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
    
    public static byte[] readFileBytes(File file) {
        byte[] fileBytes = null;
        try (BufferedInputStream reader =
                new BufferedInputStream(new FileInputStream(file))) {
            fileBytes = reader.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBytes;
    }
    
    public static void writeToFile(byte[] bytes, File file) {
        try (BufferedOutputStream writer =
                     new BufferedOutputStream(new FileOutputStream(file))) {
            writer.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static byte[] toByteArray(char[] chars) {
        byte[] bytes = new byte[chars.length];
        IntStream.range(0, chars.length)
                .forEach(i -> bytes[i] = (byte) chars[i]);
        return bytes;
    }
    
    public static String byteToBinaryString(byte b) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(b));
        while (sb.length() < 8) {
            sb.insert(0, '0');
        }
        return sb.toString().substring(sb.length() - 8, sb.length());
    }
}
