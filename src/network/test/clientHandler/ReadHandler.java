package network.test.clientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static util.MyLogger.log;

public class ReadHandler implements Runnable{

    private DataInputStream input;

    public ReadHandler(DataInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {

        try {
            while(true) {
                String received = input.readUTF();
                log(received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
