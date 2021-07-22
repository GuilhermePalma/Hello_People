package com.example.hellopeople;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggedUser extends AppCompatActivity {

    private String name;
    private String password;

    private String ip_user = "";
    private String json_dataIp = "";
    private String json_hello = "";


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

            // Get IP, IP Data, Helllo in Language for IP
            ip_user = getIp();
            json_dataIp = getDataPersonIp(ip_user);
            json_hello = getHelloForIp(ip_user);

            // Process the data in Activity
            handler.post(() -> {

                Log.e("IP", "IP for User: " + ip_user);

                // Show IP Data
                Ip ip = serialisationDataIp(json_dataIp);
                if (ip != null){
                    if (ip.getMessage_error() == null){

                        Log.i("DATA IP", String.format("Country: %s " +
                                "\nCountryCode: %s\nRegion: %s\nRegionCode: %s\nCity: %s" +
                                "\nTimezone: %s\nOrganization: %s\nIsMobile: %s",
                                ip.getCountry(), ip.getCountry_code(), ip.getRegion(),
                                ip.getRegion_code(),ip.getCity(),ip.getTimeZone(),
                                ip.getOrg(), ip.isMobile()));

                        // TODO ---> Show Window

                    } else {
                        Log.i("DATA IP", "Messge Error: " + ip.getMessage_error());
                    }
                } else {
                    // TODO ---> Add. ToastError
                    Log.e("ERROR IP", "Unable to retrieve data from IP");
                }


                // Show Hello Data
                Hello hello = serialisationHello(json_hello);
                if (hello != null){
                    Log.i("HELLO", "Langue: " + hello.getLanguage() +
                            "\nHello: " + hello.getHello());

                    // TODO ---> Show Window

                } else {
                    // TODO ---> Add. ToastError
                    Log.e("ERROR HELLO", "Unable to retrieve 'hello' by IP");
                }

            });
        });

    }

    private String getIp(){
        return SearchInternet.searchByUrl(URL_GET_IP, "GET");
    }

    private String getDataPersonIp(String ip){

        final String PARAMETER_FIELD = "fields";
        final String fields = "status,message,country,countryCode,region,regionCode," +
                "regionName,city,timezone,org,mobile,query";

            Uri buildURI = Uri.parse(URL_GET_DATA).buildUpon()
                    .appendPath(ip)
                    .appendQueryParameter(PARAMETER_FIELD, fields)
                    .build();

            return SearchInternet.searchByUrl(buildURI.toString(), "GET");
    }

    private String getHelloForIp(String ip){

        final String PARAMETER_IP = "ip";

        Uri uriBuild = Uri.parse(URL_GET_HELLO).buildUpon()
                .appendQueryParameter(PARAMETER_IP, ip)
                .build();

        return SearchInternet.searchByUrl(uriBuild.toString(), "GET");

    }
    
    private Ip serialisationDataIp(String data){

        try {

              String status, message, country, country_code, region, region_code,
                      city, timeZone, org;
              boolean isMobile;

            JSONObject jsonObject = new JSONObject(data);

            try {
                status = jsonObject.getString("status");

                if (status.equals("fail")){
                    message = jsonObject.getString("message");

                    Ip ip = new Ip();
                    ip.setMessage_error(message);

                    return ip;
                }

                country = jsonObject.getString("country");
                country_code = jsonObject.getString("countryCode");
                region_code = jsonObject.getString("region");
                region = jsonObject.getString("regionName");
                city = jsonObject.getString("city");
                timeZone = jsonObject.getString("timezone");
                org = jsonObject.getString("org");
                isMobile = jsonObject.getBoolean("mobile");

                return new Ip(country, country_code, region, region_code, city,
                        timeZone, org, isMobile);

            } catch (Exception e){
                Log.e("ERROR RECOVERY", "Error retrieving data from IP\n" + e);
                e.printStackTrace();
                return null;
            }

        } catch (JSONException e) {
            Log.e("ERROR JSON","Error in creation JSON\n" + e);
            e.printStackTrace();
            return null;
        }
    }

    private Hello serialisationHello(String data){

        try {

            String langue, hello;

            JSONObject jsonObject = new JSONObject(data);

            try {
                langue = jsonObject.getString("code");

                if (langue.equals("none")){
                    langue = "Not Recognized";
                }

                hello = jsonObject.getString("hello");

                return new Hello(langue, hello);

            } catch (Exception e){
                Log.e("ERROR RECOVERY", "Error retrieving data from IP\n" + e);
                e.printStackTrace();
                return null;
            }

        } catch (JSONException e) {
            Log.e("ERROR JSON","Error in creation JSON\n" + e);
            e.printStackTrace();
            return null;
        }

    }

}