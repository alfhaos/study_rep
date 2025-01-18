package network.tcp.v5;


import network.tcp.v5.SessionV5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class SeverV5 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소캣 시작 - 리스닝 포트 : " +PORT);

        while (true) {
            Socket socket = serverSocket.accept(); // 블로킹
            log("소켓 연결 : " + socket);

            SessionV5 session = new SessionV5(socket);

            Thread thread = new Thread(session);
            thread.start();

        }

    }
}
