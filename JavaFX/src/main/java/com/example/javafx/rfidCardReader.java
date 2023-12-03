package com.example.javafx;

import com.fazecast.jSerialComm.SerialPort;
import javafx.event.ActionEvent;

import java.util.Scanner;

public class rfidCardReader {
    private static String rfid;
    static public String getRfid()
    {
        String cardNumber = new String();
        SerialPort serialPort = SerialPort.getCommPort("COM3");

        if (serialPort.openPort()) {
            System.out.println("Serial port opened successfully.");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (serialPort.bytesAvailable() > 0) {
                    byte[] readBuffer = new byte[serialPort.bytesAvailable()];
                    int numRead = serialPort.readBytes(readBuffer, readBuffer.length);

                    if (numRead > 0) {
                        cardNumber = new String(readBuffer).trim();
                        System.out.println(cardNumber);
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!cardNumber.isEmpty())
                {
                    serialPort.closePort();
                    serialPort = null;
                    break;
                }
            }
            rfid = cardNumber;
        } else {
            System.out.println("Error opening serial port.");
        }
        return rfid;
    }
}
