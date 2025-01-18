package network.test;

public enum Command {

    EXIT("/exit"),
    JOIN("/join"),
    MESSAGE("/message"),
    CHANGE("/change"),
    USERS("/users");

    private final String content;

    Command(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
