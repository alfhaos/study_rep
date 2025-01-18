package network.test;

import network.tcp.SocketCloseUtil;
import network.tcp.v6.SessionManagerV6;
import network.test.constant.PublicMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class Session implements Runnable{

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    private final SessionManager sessionManager;
    private boolean closed = false;

    private User user;

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output =  new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        try{
            while(true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();
                String[] arr = received.split("\\|");

                if(arr[0].equals(Command.EXIT.getContent())){
                    sessionManager.messageAll(user.getName() + PublicMessage.EXIT.getMsg());
                    break;
                } else if(arr[0].equals(Command.JOIN.getContent())) {
                    this.sessionManager.add(this);
                    this.user = new User(arr[1]);
                    sessionManager.joinUser(this);
                } else if(arr[0].equals(Command.MESSAGE.getContent())) {
                    sessionManager.messageAll(user.getName() + " : " + arr[1]);
                } else if(arr[0].equals(Command.CHANGE.getContent())) {
                    String oldName = user.getName();
                    user.setName(arr[1]);
                    sessionManager.messageAll(oldName + " -> " + user.getName() + PublicMessage.CHANGE.getMsg());
                } else if(arr[0].equals(Command.USERS.getContent())) {
                    sessionManager.userList(this);
                }

            }


        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }


        log("연결 종료 : " + socket.isClosed());

    }

    //세션 종료시 서버 종료시 동시에 호출될 수 있다.
    public synchronized void close() {
        if (closed) {
            return;
        }
        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료 : " + socket);
    }

    public DataOutputStream getOutput() {
        return this.output;
    }

    public User getUser() {
        return this.user;
    }
}
