package com.example.hellopeople;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LoggedUser extends AppCompatActivity {

    private String name;
    private String password;

    private final String NAME_PREFERENCES = "LOGIN";
    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user);

        getData();

        searchMessage();


    }

    private void getData() {
        SharedPreferences registerLogin = getSharedPreferences(NAME_PREFERENCES,0);

        name = registerLogin.getString(NAME_LOGIN, "User");
        password = registerLogin.getString(PASSWORD_LOGIN, "");

        User user = new User(name,password);
    }

    private void searchMessage() {
    }

}