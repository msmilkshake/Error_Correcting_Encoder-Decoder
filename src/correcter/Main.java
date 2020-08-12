package correcter;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Random R = new Random();
    private static final String OTHER = " 0123456789";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHARS = OTHER + LOWER + LOWER.toUpperCase();
    
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        
        String in = scn.nextLine();
        System.out.println(in);
        in = encode(in);
        System.out.println(in);
        in = send(in);
        System.out.println(in);
        in = decode(in);
        System.out.println(in);
    }
    
    private static String encode(String txt) {
        StringBuilder sb = new StringBuilder();
        txt.chars().mapToObj(c -> (char) c)
                .forEach(c -> sb.append(c).append(c).append(c));
        return sb.toString();
    }
    
    private static String send(String txt) {
        StringBuilder sb = new StringBuilder(txt);
        for (int i = 0;; i += 3) {
            int rndIndex = R.nextInt(3) + i;
            if (rndIndex >= txt.length()) {
                break;
            }
            sb.setCharAt(rndIndex, CHARS.charAt(R.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
    
    private static String decode(String txt) {
        StringBuilder sb = new StringBuilder();
        Matcher m = Pattern.compile("...").matcher(txt);
        while (m.find()) {
            char c = m.group().charAt(0) == m.group().charAt(1)
                    ? m.group().charAt(0)
                    : m.group().charAt(2);
            sb.append(c);
        }
        return sb.toString();
    }
}
