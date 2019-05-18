package com.example.locationapp;

public class User {
    /** Intialising fields */
    private int id = 0;
    private String username = null;
    private String email = null;
    private static User user = null;

    /**
     *
     * @param id The uesrs ID
     * @param username The username of the user.
     * @param email The users email
     */
    public User(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
        user = this;
    }

    /**
     *
     * @return The users id.
     */
    public int getId() {
        return this.id;
    }

    /**
     *
     * @return The user's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @return The user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     *
     * @return The logged in user's object.
     */
    public static User getUser() {
        return user;
    }

    /**
     * Logout the user by removing the user from teh static object
     */
    public static void logout(){ user = null; }

}
