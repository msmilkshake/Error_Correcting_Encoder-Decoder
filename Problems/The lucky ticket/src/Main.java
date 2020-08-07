import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String num = new Scanner(System.in).nextLine();
        String[] digits = num.split("");
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i  < 3; ++i) {
            sum1 += Integer.parseInt(digits[i]);
            sum2 += Integer.parseInt(digits[i + 3]);
        }
        if (sum1 == sum2) {
            System.out.println("Lucky");
        } else {
            System.out.println("Regular");
        }
    }
}