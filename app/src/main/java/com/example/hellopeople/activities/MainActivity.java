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
    private Button btn_logout;

    private final String NAME_PREFERENCES = "LOGIN";
    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.edit_name);
        password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        btn_logout = findViewById(R.id.btn_clear);
        layout_name = findViewById(R.id.layout_name);
        layout_password = findViewById(R.id.layout_password);

        listenerLogin();
        listenerLogout();
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layout_name.setBoxStrokeColor(getColor(R.color.red));
                }

            } else if (text_password.equals("")){
                password.setError(getString(R.string.error_inputs, "Password"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layout_password.setBoxStrokeColor(getColor(R.color.red));
                }

            } else{

                SharedPreferences registerLogin = getSharedPreferences(NAME_PREFERENCES,0);

                registerLogin.edit().putString(NAME_LOGIN, text_name).apply();
                registerLogin.edit().putString(PASSWORD_LOGIN, text_password).apply();

                startActivity(new Intent(this, LoggedUser.class));
                finish();

            }
        });
    }

    private void listenerLogout() {
        btn_logout.setOnClickListener(v ->{

            login.setText("");
            password.setText("");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_name.setBoxStrokeColor(getColor(R.color.purple_500));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_password.setBoxStrokeColor(getColor(R.color.purple_500));
            }

        });
    }

}