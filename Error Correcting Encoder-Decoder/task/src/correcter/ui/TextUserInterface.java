package correcter.ui;

import correcter.logic.*;
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
        byte[] sendBytes = Tool.readFileCharsAsBytes(SEND_FILE);
        byte[] encodedBytes = HammingEncoder.bitwiseEncoder(sendBytes);
        Tool.writeToFile(encodedBytes, ENCODED_FILE);
    }
    
    private void send() {
        byte[] encodedBytes = Tool.readFileBytes(ENCODED_FILE);
        byte[] receivedBytes = ErrorSimulator.bitwiseErrors(encodedBytes);
        Tool.writeToFile(receivedBytes, RECEIVED_FILE);
    }
    
    private void decode() {
        byte[] receivedBytes = Tool.readFileBytes(RECEIVED_FILE);
        byte[] decodedBytes = HammingDecoder.bitwiseDecode(receivedBytes);
        Tool.writeToFile(decodedBytes, DECODED_FILE);
    }
}