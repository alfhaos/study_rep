package io.security.cors2;

public class User {

    private String username;

    private int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAte() {
        return age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAte(int age) {
        this.age = age;
    }
}
