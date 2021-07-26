package com.example.hellopeople.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellopeople.R;
import com.example.hellopeople.model.User;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layout_language;

    private TextInputEditText login;
    private TextInputEditText password;

    private TextInputLayout layout_name;
    private TextInputLayout layout_password;
    private AutoCompleteTextView input_language;

    private SwitchMaterial switch_languages;

    private Button btn_login;
    private Button btn_clear;
    private ImageButton help;

    private TextView hasLogin;

    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";
    private final String FIRST_LOGIN = "FIRST_LOGIN";
    private final String LANGUAGE_CHOISED = "LANGUAGE_CHOISED";

    private User user;
    private SharedPreferences sharedPreferences;
    private String language_choised;
    private String[] languages;
    private String[] code_languages;

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
        input_language = findViewById(R.id.autoCompleteLanguage);
        switch_languages = findViewById(R.id.switch_languages);
        layout_language = findViewById(R.id.layout_language);
        help = findViewById(R.id.imgbtn_help);

        languages = getResources().getStringArray(R.array.language);
        code_languages = getResources().getStringArray(R.array.code_language);
        language_choised = "";

        setButtonClear();
        setUpInputLanguage();

        switch_languages.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layout_language.setVisibility(View.VISIBLE);
            } else {
                layout_language.setVisibility(View.GONE);
            }
        });

        input_language.setOnItemClickListener((parent, view, position, id) ->
                language_choised = code_languages[position]);

        listenerHelp();
        listenerLogin();
        listenerClear();
    }

    // Listener do Button Help
    private void listenerHelp() {
        help.setOnClickListener( v-> {
            AlertDialog alert_help = new AlertDialog.Builder(this).create();

            alert_help.setTitle(getString(R.string.title_help));
            alert_help.setMessage(getString(R.string.step_oneHelp) + "\n" +
                    getString(R.string.step_twoHelp) + "\n" + getString(R.string.step_threeHelp));

            alert_help.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    (dialog, which) -> alert_help.dismiss());

            alert_help.show();
        });
    }

    private void setUpInputLanguage() {
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, languages);
        input_language.setAdapter(adapterLanguage);
    }

    private boolean existsLogin() {
        return sharedPreferences.getString(NAME_LOGIN, null) != null;
    }

    private void setButtonClear(){
        if (existsLogin()){
            btn_clear.setText(R.string.btn_logout);
            hasLogin.setVisibility(View.VISIBLE);
        } else {
            btn_clear.setText(R.string.btn_clear);
            hasLogin.setVisibility(View.GONE);
        }
    }

    private void listenerLogin() {
        btn_login.setOnClickListener(v -> {

            if (!connectionAvailable()) {
                Toast.makeText(this, R.string.error_internet, Toast.LENGTH_LONG)
                        .show();
            } else if (filledInputs()) {
                
                if (!language_choised.equals("")){
                    sharedPreferences.edit().putString(LANGUAGE_CHOISED, language_choised).apply();
                }
                

                if (existsLogin()) {
                    
                    if (isCorrectLogin(user.getName(), user.getPassword())) {
                        sharedPreferences.edit().putBoolean(FIRST_LOGIN, false).apply();
                        startActivity(new Intent(this, LoggedUser.class));
                        finish();
                    } else {
                        Toast.makeText(this, R.string.incorrect_login, Toast.LENGTH_LONG)
                                .show();
                    }
                    
                } else {
                    sharedPreferences.edit().putString(NAME_LOGIN, user.getName()).apply();
                    sharedPreferences.edit().putString(PASSWORD_LOGIN, user.getPassword()).apply();
                    sharedPreferences.edit().putBoolean(FIRST_LOGIN, true).apply();

                    startActivity(new Intent(this, LoggedUser.class));
                    finish();
                }
            }
        });
    }

    private boolean connectionAvailable(){
        //Get Internet Service
        ConnectivityManager connectionManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;

        if (connectionManager != null) {
            networkInfo = connectionManager.getActiveNetworkInfo();

            // Get if exists connection
            if (networkInfo != null && networkInfo.isConnected()){
                return true;
            } else{
                // Connection is not Available
                Log.e("NO CONECTED", "\nInternet not Connected" +
                        "\nConection Infos: " + networkInfo);
                return false;
            }

        } else{
            Log.e("NO SERVICE", "\n Error in Connectivity Service" +
                    "\nService: " + connectionManager);
            return false;
        }
    }

    private boolean filledInputs(){
        String text_name, text_password;

        try {
            text_name = Objects.requireNonNull(login.getText()).toString();
            text_password = Objects.requireNonNull(password.getText()).toString();
        } catch (Exception ex){
            Log.e("RECOVERY", "Error retrieving data from fields:\n" + ex);
            return false;
        }

        if (text_name.equals("")){
            login.setError(getString(R.string.error_inputs, "Name"));
            login.requestFocus();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_name.setBoxStrokeColor(getColor(R.color.red));
            }
            return false;

        } else if (text_password.equals("")){
            password.setError(getString(R.string.error_inputs, "Password"));
            password.requestFocus();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_password.setBoxStrokeColor(getColor(R.color.red));
            }
            return false;

        } else if(layout_language.getVisibility() == View.VISIBLE &&
                input_language.getText().toString().equals("")){

                layout_language.setError(getString(R.string.error_inputs, "Language"));
                layout_language.requestFocus();
                return false;
        } else {
            user = new User(text_name,text_password,language_choised);
            return true;
        }
    }

    private void listenerClear() {
        btn_clear.setOnClickListener(v ->{

            login.setText("");
            password.setText("");
            input_language.setText("",null);

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
                setButtonClear();
            }
        });
    }

    private boolean isCorrectLogin(String name, String password){

        String name_salve = sharedPreferences.getString(NAME_LOGIN, "");
        String password_save = sharedPreferences.getString(PASSWORD_LOGIN, "");

        return name_salve.equals(name) && password_save.equals(password);
    }

}