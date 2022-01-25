package com.example.hellopeople.activities;

import android.content.Context;
import android.content.Intent;
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
import com.example.hellopeople.entity.User;
import com.example.hellopeople.utils.ManagerSharedPreferences;
import com.example.hellopeople.utils.Resources;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ManagerSharedPreferences sharedPreferences;
    private TextInputLayout layout_language;

    private TextInputEditText login;
    private TextInputEditText password;

    private TextInputLayout layout_name;
    private TextInputLayout layout_password;
    private AutoCompleteTextView input_language;

    private Button btn_clear;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instacia os Itens Necessarios
        instanceItems();

        // Configura os Botões Exibidos e o Input dos Idiomas
        setButtonForm();
        setUpLanguage();

        // Listener dos Botões do APP
        listenerHelp();
        listenerLogin();
        listenerClear();
    }

    private void instanceItems() {
        context = MainActivity.this;
        sharedPreferences = new ManagerSharedPreferences(context);
        user = new User();

        login = findViewById(R.id.edit_name);
        password = findViewById(R.id.edit_password);
        btn_clear = findViewById(R.id.btn_clear);
        layout_name = findViewById(R.id.layout_name);
        layout_password = findViewById(R.id.layout_password);
        input_language = findViewById(R.id.autoCompleteLanguage);
        layout_language = findViewById(R.id.layout_language);
    }

    /**
     * Configura os Botões que serão exibidos no Formulario de Login
     */
    private void setButtonForm() {
        TextView hasLogin = findViewById(R.id.text_hasLogin);
        if (sharedPreferences.hasLogin()) {
            btn_clear.setText(R.string.btn_logout);
            hasLogin.setVisibility(View.VISIBLE);
        } else {
            btn_clear.setText(R.string.btn_clear);
            hasLogin.setVisibility(View.GONE);
        }
    }

    /**
     * Configura o Input que exibe os Idiomas do "Ola"
     */
    private void setUpLanguage() {
        // Obtem os Arrays dos Dados Usados para a Seleção do Idioma
        String[] languages = getResources().getStringArray(R.array.language);
        String[] code_languages = getResources().getStringArray(R.array.code_language);

        // Configura a Lista de Idiomas Disponiveis
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, languages);
        input_language.setAdapter(adapterLanguage);

        // Obtem o Codigo do Idioma que o Usuario Selecionou
        input_language.setOnItemClickListener((parent, view, position, id) ->
                user.setCode_language(code_languages[position]));


        // Configura o Switch que exibe ou não o Input dos Idiomas
        SwitchMaterial switch_languages = findViewById(R.id.switch_languages);
        switch_languages.setOnCheckedChangeListener((buttonView, isChecked) ->
                layout_language.setVisibility(isChecked ? View.VISIBLE : View.GONE));
    }

    /**
     * Listener do Button Help
     */
    private void listenerHelp() {
        ImageButton imgBtn_help = findViewById(R.id.imgbtn_help);
        imgBtn_help.setOnClickListener(v -> {
            // Confifura e Exibe um AlertDialog ao Clicar no Botão
            AlertDialog alert_help = new AlertDialog.Builder(context).create();

            alert_help.setTitle(getString(R.string.title_help));
            alert_help.setMessage(getString(R.string.step_useApp));

            alert_help.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    (dialog, which) -> alert_help.dismiss());

            alert_help.show();
        });
    }

    /**
     * Metodo Responsavel pelo Listener do Botão Login
     */
    private void listenerLogin() {
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> {
            // Verifica se há conexão com a Internet e se os Inputs forão Preenchidps
            if (!Resources.hasConnectionAvailable(context)) {
                Toast.makeText(context, R.string.error_internet, Toast.LENGTH_LONG).show();
            } else if (isFilledInputs()) {
                // Verifica se já existe um User Logado
                if (sharedPreferences.hasLogin()) {
                    // Compara os Dados inseridos com os Dados Salvos do User
                    if (sharedPreferences.checkCredentialUser(user)) {
                        // Caso o Usuario tenha Selecionado um Idioma, salva ele nas SharedPreferences
                        if (!Resources.stringIsNullOrEmpty(user.getCode_language())) {
                            sharedPreferences.setLanguage(user.getCode_language());
                        }
                        sharedPreferences.setFirstLogin(false);
                        startActivity(new Intent(context, LoggedUser.class));
                        finish();
                    } else {
                        Toast.makeText(context, R.string.incorrect_login, Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Como não há User logado, salva as Informações do User nas SharedPreferences
                    sharedPreferences.setUserPreferences(user);
                    sharedPreferences.setFirstLogin(true);
                    startActivity(new Intent(context, LoggedUser.class));
                    finish();
                }
            }
        });
    }


    /**
     * Verifica os dados dos Inputs e caso haja Erro, exibe
     *
     * @return true|false
     */
    private boolean isFilledInputs() {
        String text_name, text_password;

        try {
            text_name = Objects.requireNonNull(login.getText()).toString();
            text_password = Objects.requireNonNull(password.getText()).toString();
        } catch (Exception ex) {
            Log.e("RECOVERY", "Error retrieving data from fields:\n" + ex.getClass().getName());
            ex.printStackTrace();
            return false;
        }

        // Verifica os Inputs, e caso Haja algum Erro, exibe nos Inputs
        if (Resources.stringIsNullOrEmpty(text_name)) {

            login.setError(getString(R.string.error_inputs, "Name"));
            login.requestFocus();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_name.setBoxStrokeColor(getColor(R.color.red));
            }
            return false;
        } else if (Resources.stringIsNullOrEmpty(text_password)) {

            password.setError(getString(R.string.error_inputs, "Password"));
            password.requestFocus();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_password.setBoxStrokeColor(getColor(R.color.red));
            }
            return false;
        } else if (layout_language.getVisibility() == View.VISIBLE &&
                Resources.stringIsNullOrEmpty(user.getCode_language())) {

            layout_language.setError(getString(R.string.error_inputs, "Language"));
            layout_language.requestFocus();
            return false;
        } else {
            user.setName(text_name);
            user.setPassword(text_password);
            return true;
        }
    }

    /**
     * Listener do Botão "Clear", que limpa as Informações do Form e das SharedPreferences
     */
    private void listenerClear() {
        btn_clear.setOnClickListener(v -> {
            login.setText("");
            password.setText("");
            input_language.setText("", null);

            login.setError(null);
            password.setError(null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_name.setBoxStrokeColor(getColor(R.color.purple_500));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layout_password.setBoxStrokeColor(getColor(R.color.purple_500));
            }

            if (sharedPreferences.hasLogin()) {
                // Limpa a SharedPrefernces e Atualiza o Layout dos Botões
                sharedPreferences.resetSharedPreferences();
                setButtonForm();
            }
        });
    }

}