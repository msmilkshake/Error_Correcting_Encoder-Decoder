package correcter;

import correcter.logic.HammingEncoder;
import correcter.ui.TextUserInterface;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        new TextUserInterface(new Scanner("encode")).start();
    }
}
