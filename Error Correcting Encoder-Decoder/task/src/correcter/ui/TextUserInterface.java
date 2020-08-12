package correcter.ui;

import correcter.logic.Decoder;
import correcter.logic.Encoder;
import correcter.logic.ErrorSimulator;
import correcter.logic.HammingEncoder;
import correcter.tool.Timer;
import correcter.tool.Tool;

import java.io.File;
import java.util.Scanner;

public class TextUserInterface {
    private final Scanner SCN;
    private File SEND_FILE;
    private File ENCODED_FILE;
    private File RECEIVED_FILE;
    private File DECODED_FILE;
    
    private Timer timer;
    
    public TextUserInterface(Scanner scn) {
        this.SCN = scn;
        SEND_FILE = new File("send.txt");
        ENCODED_FILE = new File("encoded.txt");
        RECEIVED_FILE = new File("received.txt");
        DECODED_FILE = new File("decoded.txt");
        timer = new Timer();
    }
    
    public void start() {
        System.out.print("Write a mode: ");
        String inputMode = SCN.nextLine();
        
        switch (inputMode) {
            case "encode":
                encode();
                break;
            case "send":
                send();
                break;
            case "decode":
                decode();
                break;
            case "all":
                encode();
                send();
                decode();
                break;
            default:
                break;
        }
    }
    
    private void encode() {
        timer.start();
        byte[] sendBytes = Tool.readFileCharsAsBytes(SEND_FILE);
        timer.stop();
        System.out.println("File chars loaded in " + timer.get() + "ms");
        timer.start();
        byte[] encodedBytes = HammingEncoder.bitwiseEncoder(sendBytes);
        timer.stop();
        System.out.println("Bitwise encoding took " + timer.get() + "ms");
        Tool.writeToFile(encodedBytes, ENCODED_FILE);
    }
    
    private void send() {
        timer.start();
        byte[] encodedBytes = Tool.readFileBytes(ENCODED_FILE);
        timer.stop();
        System.out.println("File bytes loaded in " + timer.get() + "ms");
        timer.start();
        byte[] receivedBytes = ErrorSimulator.stringManipErrors(encodedBytes);
        timer.stop();
        System.out.println("String errors took " + timer.get() + "ms");
        timer.start();
        receivedBytes = ErrorSimulator.bitwiseErrors(encodedBytes);
        timer.stop();
        System.out.println("Bitwise errors took " + timer.get() + "ms");
        Tool.writeToFile(receivedBytes, RECEIVED_FILE);
    }
    
    private void decode() {
        timer.start();
        byte[] receivedBytes = Tool.readFileBytes(RECEIVED_FILE);
        timer.stop();
        System.out.println("File bytes loaded in " + timer.get() + "ms");
        timer.start();
        byte[] decodedBytes = Decoder.stringManipDecode(receivedBytes);
        timer.stop();
        System.out.println("String decoding took " + timer.get() + "ms");
        timer.start();
        decodedBytes = Decoder.bitwiseDecode(receivedBytes);
        Tool.writeToFile(decodedBytes, DECODED_FILE);
        timer.stop();
        System.out.println("Bitwise decoding took " + timer.get() + "ms");
    }
}
