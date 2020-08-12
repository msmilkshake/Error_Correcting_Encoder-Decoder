package correcter;

import java.util.Random;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Random r = new Random();
        String other = " 0123456789";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String chars = other + lower + lower.toUpperCase();
        
        String in = scn.nextLine();
        
        StringBuilder sb = new StringBuilder(in);
        for (int i = 0;; i += 3) {
            int rndIndex = r.nextInt(3) + i;
            if (rndIndex >= in.length()) {
                break;
            }
            sb.setCharAt(rndIndex, chars.charAt(r.nextInt(chars.length())));
        }
        
        System.out.println(sb.toString());
    }
}
