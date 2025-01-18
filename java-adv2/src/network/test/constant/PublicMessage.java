package network.test.constant;

public enum PublicMessage {
    EXIT(" 님이 퇴장하셨습니다."),

    JOIN(" 님이 입장하셨습니다."),

    SENDMESSAGE("/message|"),
    CHANGE(" 이름을 변경하셨습니다.");

    private final String msg;

    PublicMessage(String content) {
        this.msg = content;
    }

    public String getMsg() {
        return msg;
    }
}
