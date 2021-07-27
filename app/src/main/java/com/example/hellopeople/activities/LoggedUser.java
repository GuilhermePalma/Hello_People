package com.example.hellopeople.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hellopeople.R;
import com.example.hellopeople.model.Hello;
import com.example.hellopeople.model.Ip;
import com.example.hellopeople.model.SearchInternet;
import com.example.hellopeople.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggedUser extends AppCompatActivity {

    private LinearLayout layout_more;
    private Button button_logout;
    private Button button_more;
    private TextView txt_logged;
    private TextView txt_goodday;
    private TextView txt_ip;
    private TextView txt_language;
    private TextView txt_errorMessage;
    private TextView txt_country;
    private TextView txt_region;
    private TextView txt_city;
    private TextView txt_timezone;
    private TextView txt_org;
    private TextView txt_mobile;
    private TextView txt_search;
    private ProgressBar process_loading;

    private String ip_user = "";
    private String json_dataIp = "";
    private String json_hello = "";

    private static final String URL_GET_IP = "http://checkip.amazonaws.com";
    private static final String URL_GET_DATA = "http://ip-api.com/json";
    private static final String URL_GET_HELLO = "https://fourtonfish.com/hellosalut";

    private final String NAME_LOGIN = "NAME";
    private final String PASSWORD_LOGIN = "PASSWORD";
    private final String FIRST_LOGIN = "FIRST_LOGIN";
    private final String LANGUAGE_CHOISED = "LANGUAGE_CHOISED";

    private User user;
    private ExecutorService executor;
    private Handler handler;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user);

        handler = new Handler(Looper.getMainLooper());
        setItemsId();

        executor = Executors.newSingleThreadExecutor();

        process_loading.setProgress(0);
        process_loading.setMax(10);

        String NAME_PREFERENCES = "LOGIN";
        preferences = getSharedPreferences(NAME_PREFERENCES,0);
        user = getUserPreferences();

        // Initialization of the Async Method that obtains Activity information
        searchAsyncInternet();
        listenerLogout();
        listenerSeeMore();
    }

    private void setItemsId() {
        layout_more = findViewById(R.id.layout_more);
        button_logout = findViewById(R.id.btn_logout);
        button_more = findViewById(R.id.btn_more);
        txt_logged = findViewById(R.id.text_logged);
        txt_goodday = findViewById(R.id.text_goodday);
        txt_ip = findViewById(R.id.text_ip);
        txt_language = findViewById(R.id.text_language);
        txt_errorMessage = findViewById(R.id.text_errorMessage);
        txt_country = findViewById(R.id.text_country);
        txt_region = findViewById(R.id.text_region);
        txt_city = findViewById(R.id.text_city);
        txt_timezone = findViewById(R.id.text_timezone);
        txt_org = findViewById(R.id.text_org);
        txt_mobile = findViewById(R.id.text_mobile);
        txt_search = findViewById(R.id.text_search);
        process_loading = findViewById(R.id.progressBar_loading);
    }

    private void listenerLogout() {
        button_logout.setOnClickListener( v-> {

            preferences.edit().putString(NAME_LOGIN, null).apply();
            preferences.edit().putString(PASSWORD_LOGIN, null).apply();
            preferences.edit().putString(LANGUAGE_CHOISED, null).apply();
            preferences.edit().putBoolean(FIRST_LOGIN, true).apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void listenerSeeMore() {
        button_more.setOnClickListener( v-> {
            if (layout_more.getVisibility() == View.VISIBLE){
                button_more.setText(R.string.btn_moreDefault);
                layout_more.setVisibility(View.GONE);
            } else{
                button_more.setText(R.string.btn_moreAfterClick);
                layout_more.setVisibility(View.VISIBLE);
            }
        });
    }

    private void searchAsyncInternet() {
        executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            // Get Extern IP Address
            ip_user = getIp();

            if (ip_user.equals("")){
                // Change Data in Window
                handler.post(() -> {
                    process_loading.setProgress(10);
                    process_loading.setVisibility(View.GONE);
                    txt_search.setText(R.string.error_search);
                    Toast.makeText(this, R.string.message_noIp, Toast.LENGTH_LONG)
                            .show();
                });
                return;
            }

            json_dataIp = getDataPersonIp(ip_user);
            json_hello = getHelloForIp(ip_user);

            if (user.getCode_language().equals("")){
                json_hello = getHelloForIp(ip_user);
            } else {
                json_hello = getHelloForLanguage(user.getCode_language());
            }

            // Process the data in Activity
            handler.post(() -> {

                // Show Ip Address
                txt_ip.setText(String.format(getString(R.string.txt_ip), ip_user));

                // Show IP Data
                Ip ip = serialisationDataIp(json_dataIp);
                if (ip != null) {
                    showDetailsIp(ip);
                } else {
                    Toast.makeText(this, R.string.error_detailsIP,
                            Toast.LENGTH_LONG).show();

                    Log.e("ERROR IP", "Unable to retrieve data from IP");
                }

                // Show Hello Data
                Hello hello = serialisationHello(json_hello);
                if (hello != null) {
                    showMessageUser(hello);
                } else {
                    Toast.makeText(this, R.string.error_hello,
                            Toast.LENGTH_LONG).show();

                    Log.e("ERROR HELLO", "Unable to retrieve 'hello' by IP");
                }

                // Show in Window the result of Async Method ---> Visible
                process_loading.setProgress(10);
                process_loading.setVisibility(View.GONE);
                txt_search.setVisibility(View.GONE);
                txt_logged.setVisibility(View.VISIBLE);
                txt_goodday.setVisibility(View.VISIBLE);
                button_more.setVisibility(View.VISIBLE);

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

    private String getHelloForLanguage(String language){

        final String PARAMETER_LANGUAGE = "lang";

        Uri uriBuild = Uri.parse(URL_GET_HELLO).buildUpon()
                .appendQueryParameter(PARAMETER_LANGUAGE, language)
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

                // Normalizing the String (HTML Tags ---> String)
                hello = Html.fromHtml(hello).toString();

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

    private void showMessageUser(Hello hello) {

        String lang = "";

        List<String> code_languages = Arrays.asList(getResources().
                getStringArray(R.array.code_language));

        if (code_languages.contains(hello.getLanguage())){
            int position = code_languages.indexOf(hello.getLanguage());
            List<String> languages = Arrays.asList(getResources().
                    getStringArray(R.array.language));
            lang = languages.get(position);
        }

        txt_language.setText(String.format(getString(R.string.txt_langue),
                lang, "- " +hello.getLanguage()));

        if (preferences.getBoolean(FIRST_LOGIN, true)){
            txt_logged.setText(String.format(
                    getString(R.string.message_successLogin), hello.getHello(), user.getName()));

            preferences.edit().putBoolean(FIRST_LOGIN, false).apply();

        } else {
            txt_logged.setText(String.format(
                    getString(R.string.message_hello), hello.getHello(), user.getName()));
        }

        txt_goodday.setText(String.format(getString(R.string.message_goodDay), user.getName()));
    }

    private void showDetailsIp(Ip ip) {

        if (ip.getMessage_error() != null) {
            Log.i("DATA IP", "Message Error: " + ip.getMessage_error());

            // Show Details Empty
            txt_errorMessage.setVisibility(View.VISIBLE);
            txt_country.setVisibility(View.GONE);
            txt_region.setVisibility(View.GONE);
            txt_city.setVisibility(View.GONE);
            txt_timezone.setVisibility(View.GONE);
            txt_org.setVisibility(View.GONE);
            txt_mobile.setVisibility(View.GONE);

            txt_errorMessage.setText(String.format(getString
                    (R.string.txt_errorMessage), ip.getMessage_error()));
        } else{

            // If the details is Not Visible
            if (txt_errorMessage.getVisibility() == View.VISIBLE){
                txt_errorMessage.setVisibility(View.GONE);
                txt_country.setVisibility(View.VISIBLE);
                txt_region.setVisibility(View.VISIBLE);
                txt_city.setVisibility(View.VISIBLE);
                txt_timezone.setVisibility(View.VISIBLE);
                txt_org.setVisibility(View.VISIBLE);
                txt_mobile.setVisibility(View.VISIBLE);
            }

            // Show the IP Details
            txt_country.setText(String.format(
                    getString(R.string.txt_country), ip.getCountry(), ip.getCountry_code()));
            txt_region.setText(String.format(
                    getString(R.string.txt_region), ip.getRegion(), ip.getRegion_code()));
            txt_city.setText(String.format(getString(R.string.txt_city), ip.getCity()));
            txt_timezone.setText(String.format(getString(R.string.txt_timezone), ip.getTimeZone()));
            txt_org.setText(String.format(getString(R.string.txt_organization), ip.getOrg()));
            if (ip.isMobile()){
                // isMobile = True
                txt_mobile.setText(String.format(getString(R.string.txt_mobile), "Yes"));
            } else {
                txt_mobile.setText(String.format(getString(R.string.txt_mobile), "No"));
            }
        }
    }

    private User getUserPreferences() {

        String name = preferences.getString(NAME_LOGIN, "User");
        String password = preferences.getString(PASSWORD_LOGIN, "");
        String language = preferences.getString(LANGUAGE_CHOISED, "");

        return new User(name,password,language);
    }

}