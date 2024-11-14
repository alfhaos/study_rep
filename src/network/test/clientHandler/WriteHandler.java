package network.test.clientHandler;

import network.test.Client;
import network.test.constant.PublicMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static util.MyLogger.log;

public class WriteHandler implements Runnable{

    private DataOutputStream output;
    private Client client;

    public WriteHandler(DataOutputStream output, Client client) {
        this.output = output;
        this.client = client;
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        try {
            while(true) {
                System.out.print("전송 문자 : ");
                String toSend = scanner.nextLine();

                if (toSend.startsWith("/exit")) {
                    output.writeUTF(toSend);
                    break;
                }
                try {
                    if(toSend.startsWith("/")) {
                        output.writeUTF(toSend);
                    } else {
                        writeMessage(toSend);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

    }


    public void writeMessage(String send) throws IOException {
        output.writeUTF(PublicMessage.SENDMESSAGE.getMsg() + send);
    }
}
