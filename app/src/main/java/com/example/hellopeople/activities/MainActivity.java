package com.example.hellopeople.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView hasLogin;

    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";
    private final String FIRST_LOGIN = "FIRST_LOGIN";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String NAME_PREFERENCES = "LOGIN";
        sharedPreferences = getSharedPreferences(NAME_PREFERENCES,0);

        login = findViewById(R.id.edit_name);
        password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        btn_clear = findViewById(R.id.btn_clear);
        layout_name = findViewById(R.id.layout_name);
        layout_password = findViewById(R.id.layout_password);
        hasLogin = findViewById(R.id.text_hasLogin);

        updateInfosLogin();
        listenerLogin();
        listenerClear();
    }

    private boolean existsLogin() {
        return sharedPreferences.getString(NAME_LOGIN, null) != null;
    }

    private void updateInfosLogin(){
        if (existsLogin()){
            btn_clear.setText(R.string.btn_logout);
            hasLogin.setVisibility(View.VISIBLE);
        } else {
            btn_clear.setText(R.string.btn_clear);
            hasLogin.setVisibility(View.GONE);
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
                if (existsLogin()) {
                    if (isCorrectLogin(text_name,text_password)){
                        sharedPreferences.edit().putBoolean(FIRST_LOGIN, false).apply();
                        startActivity(new Intent(this, LoggedUser.class));
                        finish();
                    } else {
                        Toast.makeText(this, R.string.incorrect_login, Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    sharedPreferences.edit().putString(NAME_LOGIN, text_name).apply();
                    sharedPreferences.edit().putString(PASSWORD_LOGIN, text_password).apply();
                    sharedPreferences.edit().putBoolean(FIRST_LOGIN, true).apply();

                    startActivity(new Intent(this, LoggedUser.class));
                    finish();
                }
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

            if (existsLogin()){
                sharedPreferences.edit().putString(NAME_LOGIN, null).apply();
                sharedPreferences.edit().putString(PASSWORD_LOGIN, null).apply();
                sharedPreferences.edit().putBoolean(FIRST_LOGIN, true).apply();
                updateInfosLogin();
            }
        });
    }

    private boolean isCorrectLogin(String name, String password){

        String name_salve = sharedPreferences.getString(NAME_LOGIN, "");
        String password_save = sharedPreferences.getString(PASSWORD_LOGIN, "");

        return name_salve.equals(name) && password_save.equals(password);
    }

}