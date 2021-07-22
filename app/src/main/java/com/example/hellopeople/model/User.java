package com.example.hellopeople.model;

public class User {
    private String name;
    private String password;

    // Contructor for Login
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}