package network.test;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//동시성 처리
public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session) {
        sessions.add(session);

    }
    public synchronized void remove(Session session) {
        sessions.remove(session);

    }

    public List<Session> getSessions() {
        return sessions;
    }

    public synchronized void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
        sessions.clear();
    }


    public synchronized void joinUser(Session session) throws IOException {

        for (Session temp : sessions) {
            if(temp != session) {
                DataOutputStream output = temp.getOutput();
                output.writeUTF(session.getUser().getName() + " 님이 입장하셨습니다.");
            }
        }
    }


}
