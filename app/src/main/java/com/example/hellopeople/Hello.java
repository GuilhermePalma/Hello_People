package com.example.hellopeople;

public class Hello {
    private final String language;
    private final String hello;

    public Hello(String language, String hello){
        this.language = language;
        this.hello = hello;
    }

    public String getLanguage() {
        return language;
    }

    public String getHello() {
        return hello;
    }
}
