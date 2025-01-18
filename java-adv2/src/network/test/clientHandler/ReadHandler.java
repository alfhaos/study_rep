package network.test.clientHandler;

import network.test.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static util.MyLogger.log;

public class ReadHandler implements Runnable{

    private final DataInputStream input;

    private final Client client;

    public ReadHandler(DataInputStream input, Client client) {
        this.input = input;
        this.client = client;
    }

    @Override
    public void run() {

        try {
            while(true) {
                String received = input.readUTF();
                log(received);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            client.close();
        }

    }
}
