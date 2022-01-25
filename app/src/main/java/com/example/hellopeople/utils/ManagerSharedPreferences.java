package com.example.hellopeople.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hellopeople.entity.User;

public class ManagerSharedPreferences {
    // Constantes das SharedPreferences do APP
    private static final String NAME_PREFERENCE = "HELLO_PEOPLE";
    private static final String NAME_LOGIN = "NAME";
    private static final String PASSWORD_LOGIN = "PASSWORD";
    private static final String FIRST_LOGIN = "FIRST_LOGIN";
    private static final String LANGUAGE_CHOOSE = "LANGUAGE_CHOOSE";
    private static final String REMEMBER_LOGIN = "REMEMBER_LOGIN";
    final private SharedPreferences sharedPreferences;

    // Construtor da Classe
    public ManagerSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME_PREFERENCE, 0);
    }

    /**
     * Exclui todas as {@link SharedPreferences}
     */
    public void resetSharedPreferences() {
        sharedPreferences.edit().clear().apply();
    }

    /**
     * Verifica se o Usuario passado é igual ao Usuario salvo
     *
     * @param userCheck Usuario que será verificado
     * @return true|false
     */
    public boolean checkCredentialUser(User userCheck) {
        User userSaved = getUserPreferences();
        if (userSaved == null) return false;

        return userSaved.getName().equals(userCheck.getName()) &&
                userSaved.getPassword().equals(userCheck.getPassword());
    }

    /**
     * Obtem o Usuario salvo nas SharedPreferences
     *
     * @return {@link User}|null
     */
    public User getUserPreferences() {
        return new User(sharedPreferences.getString(NAME_LOGIN, null),
                sharedPreferences.getString(PASSWORD_LOGIN, null),
                sharedPreferences.getString(LANGUAGE_CHOOSE, null));
    }

    /**
     * Insere um {@link User} nas {@link SharedPreferences}
     *
     * @param user {@link User} que será inserido nas SharedPreferences
     */
    public void setUserPreferences(User user) {
        sharedPreferences.edit().putString(NAME_LOGIN, user.getName()).apply();
        sharedPreferences.edit().putString(PASSWORD_LOGIN, user.getPassword()).apply();
        sharedPreferences.edit().putString(LANGUAGE_CHOOSE, user.getCode_language()).apply();
    }

    /**
     * Salva nas {@link SharedPreferences} o Idioma do Usuario
     */
    public void setLanguage(String language) {
        sharedPreferences.edit().putString(LANGUAGE_CHOOSE, language).apply();
    }

    /**
     * Obtem nas {@link SharedPreferences} se é ou não o Primeiro Acesso do Usuario no APP
     *
     * @return true|false
     */
    public boolean isFirstLogin() {
        return sharedPreferences.getBoolean(FIRST_LOGIN, true);
    }

    /**
     * Define nas {@link SharedPreferences} se é ou não o Primeiro Acesso do Usuario no APP
     */
    public void setFirstLogin(boolean isFirstLogin) {
        sharedPreferences.edit().putBoolean(FIRST_LOGIN, isFirstLogin).apply();
    }

    /**
     * Retorna se Existe ou não um Login no APP
     *
     * @return true|false
     */
    public boolean getRememberLogin() {
        return sharedPreferences.getBoolean(REMEMBER_LOGIN, false);
    }

    /**
     * Salva nas {@link SharedPreferences} a Opção de Lembrar Login
     */
    public void setRememberLogin(boolean isRemember) {
        sharedPreferences.edit().putBoolean(REMEMBER_LOGIN, isRemember).apply();
    }
}
