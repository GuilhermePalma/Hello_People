package com.example.hellopeople.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.hellopeople.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText login;
    private TextInputEditText password;

    private TextInputLayout layout_name;
    private TextInputLayout layout_password;

    private Button btn_login;
    private Button btn_clear;

    private final String NAME_PREFERENCES = "LOGIN";
    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";
    private final String FIRST_LOGIN = "FIRST_LOGIN";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(NAME_PREFERENCES,0);
        checkLogin();

        login = findViewById(R.id.edit_name);
        password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        btn_clear = findViewById(R.id.btn_clear);
        layout_name = findViewById(R.id.layout_name);
        layout_password = findViewById(R.id.layout_password);

        listenerLogin();
        listenerClear();
    }

    private void checkLogin() {
        if (sharedPreferences.getString(NAME_LOGIN,null) != null){
            startActivity(new Intent(this, LoggedUser.class));
            finish();
        }
    }

    private void listenerLogin() {
        btn_login.setOnClickListener(v ->{

            String text_name = "", text_password ="";

            try {
                text_name = Objects.requireNonNull(login.getText()).toString();
                text_password = Objects.requireNonNull(password.getText()).toString();
            } catch (Exception ex){
                Log.e("RECOVERY", "Error retrieving data from fields:\n" + ex);
            }

            if (text_name.equals("")){
                login.setError(getString(R.string.error_inputs, "Name"));
                login.requestFocus();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layout_name.setBoxStrokeColor(getColor(R.color.red));
                }

            } else if (text_password.equals("")){
                password.setError(getString(R.string.error_inputs, "Password"));
                password.requestFocus();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layout_password.setBoxStrokeColor(getColor(R.color.red));
                }

            } else{

                sharedPreferences.edit().putString(NAME_LOGIN, text_name).apply();
                sharedPreferences.edit().putString(PASSWORD_LOGIN, text_password).apply();
                sharedPreferences.edit().putBoolean(FIRST_LOGIN, true).apply();

                startActivity(new Intent(this, LoggedUser.class));
                finish();

            }
        });
    }

    private void listenerClear() {
        btn_clear.setOnClickListener(v ->{

            login.setText("");
            password.setText("");

            login.setError(null);
            password.setError(null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_name.setBoxStrokeColor(getColor(R.color.purple_500));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_password.setBoxStrokeColor(getColor(R.color.purple_500));
            }

        });
    }

}