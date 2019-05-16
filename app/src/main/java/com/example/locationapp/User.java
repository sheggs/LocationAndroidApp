package com.example.locationapp;

public class User {
    private int id = 0;
    private String username = null;
    private String email = null;
    private static User user = null;

    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
        user = this;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public static User getUser() {
        return user;
    }


}
