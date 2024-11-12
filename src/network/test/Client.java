package network.test;

import network.tcp.SocketCloseUtil;
import network.test.clientHandler.ReadHandler;
import network.test.clientHandler.WriteHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        try{
            socket = new Socket(this.host, this.port);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            readHandler = new ReadHandler(input);
            writeHandler = new WriteHandler(output);

            Thread thread1 = new Thread(readHandler);
            Thread thread2 = new Thread(writeHandler);

            thread1.start();
            thread2.start();
        } catch (IOException e) {
            log(e);
        } finally {
            close();
        }
        log("연결 종료 : " + socket.isClosed());

    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료 : " + socket);
    }

}
