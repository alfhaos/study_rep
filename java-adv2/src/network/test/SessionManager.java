package network.test;


import network.test.constant.PublicMessage;

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
                output.writeUTF(session.getUser().getName() + PublicMessage.JOIN.getMsg());
            }
        }
    }

    public synchronized void messageAll(String msg) throws IOException {
        for (Session temp : sessions) {
            DataOutputStream output = temp.getOutput();
            output.writeUTF(msg);
        }
    }


    public void userList(Session session) throws IOException {

        for (Session temp : sessions) {
            if(temp == session) {
                DataOutputStream output = temp.getOutput();
                String[] users = userArr();
                output.writeUTF(String.join("," , users));
            }
        }
    }


    public String[] userArr() {
        String[] users = new String[sessions.size()];
        for(int i = 0 ; i < sessions.size() ; i++) {
            users[i] = sessions.get(i).getUser().getName();
        }

        return users;
    }
}
