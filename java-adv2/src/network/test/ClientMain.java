package network.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ClientMain {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        Client client = new Client("localhost", PORT);
        client.start();

    }
}
