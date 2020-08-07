import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String in = new Scanner(System.in).nextLine();
        String[] words = in.split(" ");
        // The comparator below also sorts words of the same length to
        // keep shifting the first occurence of the biggest word to the right.
        Arrays.sort(words, (e1, e2) -> e1.length() > e2.length() ? 1 : -1);
        System.out.println(words[words.length - 1]);
    }
}