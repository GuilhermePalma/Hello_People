package com.example.hellopeople.entity;

public class User {
    private String name;
    private String password;
    private String code_language;

    // Construtor Vazio da Classe
    public User() {
    }

    // Contructor for Login
    public User(String name, String password, String code_language) {
        this.name = name;
        this.password = password;
        this.code_language = code_language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode_language() {
        return code_language;
    }

    public void setCode_language(String code_language) {
        this.code_language = code_language;
    }
}
