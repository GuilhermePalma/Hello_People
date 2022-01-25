package com.example.hellopeople.entity;

import android.net.Uri;
import android.util.Log;

import com.example.hellopeople.utils.Resources;
import com.example.hellopeople.utils.SearchInternet;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Ip {
    private String country;
    private String country_code;
    private String region;
    private String region_code;
    private String city;
    private String timeZone;
    private String org;
    private boolean isMobile;

    // Empty Contructor
    public Ip() {
    }

    /**
     * Obtem um JSON com o IP do Usuario
     *
     * @return {@link String (JSON)}|""
     */
    public static String getAddressIP(ExecutorService executorService) {
        // IP que será retornado
        String ip = "";

        try {
            // Configura a Execução da Tarefa Assincrona
            Set<Callable<String>> callableTasksAPI = new HashSet<>();
            callableTasksAPI.add(() -> SearchInternet.searchByUrl(Uri.parse(SearchInternet.URL_IP), "GET"));

            // Executa as Tarefas Assincronas
            List<Future<String>> futureTasksList = executorService.invokeAll(callableTasksAPI);
            ip = futureTasksList.get(0).get();
        } catch (Exception e) {
            Log.e("ERROR JSON", "Error in JSON\n" + e.getClass().getName());
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * Obtem um JSON dos dados do IP do Usuario
     *
     * @return {@link String (JSON)}|null
     */
    public static Ip getDetailsIP(ExecutorService executorService, String ipAddress) {
        // IP que será retornado
        Ip ip = null;

        try {
            // Verifica se foi passado o IP
            if (Resources.stringIsNullOrEmpty(ipAddress)) return null;

            // Formação da URL de Busca dos Detalhes do IP
            Uri buildURI = Uri.parse(SearchInternet.URL_DATA_IP).buildUpon()
                    .appendPath(ipAddress)
                    .appendQueryParameter(SearchInternet.PARAMETER_FIELD_INFO_IP,
                            SearchInternet.ATTR_FIELD_INFO_IP)
                    .build();

            // Configura a Execução da Tarefa Assincrona
            Set<Callable<String>> callableTasksAPI = new HashSet<>();
            callableTasksAPI.add(() -> SearchInternet.searchByUrl(buildURI, "GET"));

            // Executa as Tarefas Assincronas
            List<Future<String>> futureTasksList = executorService.invokeAll(callableTasksAPI);
            String jsonDetailsIP = futureTasksList.get(0).get();

            // Verifica se o JSON não é nulo
            if (!Resources.stringIsNullOrEmpty(jsonDetailsIP)) {
                JSONObject jsonObject = new JSONObject(jsonDetailsIP);

                if (!jsonObject.isNull("status") && !jsonObject.get("status").equals("fail")) {

                    ip = new Ip();
                    ip.setCountry(jsonObject.isNull("country")
                            ? "" : jsonObject.getString("country"));
                    ip.setCountry_code(jsonObject.isNull("countryCode")
                            ? "" : jsonObject.getString("countryCode"));
                    ip.setRegion_code(jsonObject.isNull("region")
                            ? "" : jsonObject.getString("region"));
                    ip.setRegion(jsonObject.isNull("regionName")
                            ? "" : jsonObject.getString("regionName"));
                    ip.setCity(jsonObject.isNull("city")
                            ? "" : jsonObject.getString("city"));
                    ip.setTimeZone(jsonObject.isNull("timezone")
                            ? "" : jsonObject.getString("timezone"));
                    ip.setOrg(jsonObject.isNull("org")
                            ? "" : jsonObject.getString("org"));
                    ip.setMobile(!jsonObject.isNull("mobile") &&
                            jsonObject.getBoolean("mobile"));
                }
            }
        } catch (Exception e) {
            Log.e("ERROR JSON", "Error in JSON\n" + e.getClass().getName());
            e.printStackTrace();
        }
        return ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

}
