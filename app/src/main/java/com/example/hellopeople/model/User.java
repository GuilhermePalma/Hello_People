package com.example.hellopeople.model;

public class User {
    private final String name;
    private final String password;
    private final String code_language;

    // Contructor for Login
    public User(String name, String password, String code_language) {
        this.name = name;
        this.password = password;
        this.code_language = code_language;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCode_language() {
        return code_language;
    }
}
