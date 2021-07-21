package com.example.hellopeople;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggedUser extends AppCompatActivity {

    private String name;
    private String password;

    private String ip_user = "";
    private String hello = "";


    private final String NAME_PREFERENCES = "LOGIN";
    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";

    private static final String URL_GET_IP = "http://checkip.amazonaws.com";
    private static final String URL_GET_DATA = "http://ip-api.com/json";
    private static final String URL_GET_HELLO = "https://fourtonfish.com/hellosalut";

    SearchInternet researches;



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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            // obtem o Ip
            ip_user = getIp();

            dataPersonIp(ip_user);
            helloForIp(ip_user);

            handler.post(() -> {
                // Altera na Atividade Principal
            });
        });

    }

    // Obtem o IP
    private String getIp(){
        return SearchInternet.searchByUrl(URL_GET_IP, "GET");
    }

    private String dataPersonIp(String ip){

        final String PARAMETER_FIELD = "fields";
        final String fields = "status,message,country,countryCode,region," +
                "regionName,city,timezone,org,mobile,query";

            Uri buildURI = Uri.parse(URL_GET_DATA).buildUpon()
                    .appendPath(ip)
                    .appendQueryParameter(PARAMETER_FIELD, fields)
                    .build();

            return SearchInternet.searchByUrl(buildURI.toString(), "GET");
    }

    private String helloForIp(String ip){

        final String PARAMETER_IP = "ip";

        Uri uriBuild = Uri.parse(URL_GET_HELLO).buildUpon()
                .appendQueryParameter(PARAMETER_IP, ip)
                .build();

        return SearchInternet.searchByUrl(uriBuild.toString(), "GET");

    }

}