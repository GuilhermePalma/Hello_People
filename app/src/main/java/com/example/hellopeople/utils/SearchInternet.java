package com.example.hellopeople.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe Responsavel por Realizar Pesquisas em HTTP
 */
public class SearchInternet {

    /**
     * URL de para Obter o IP do Usuario
     */
    public static final String URL_IP = "http://checkip.amazonaws.com";
    /**
     * URL para Obter os Dados do IP do Usuario
     */
    public static final String URL_DATA_IP = "http://ip-api.com/json";
    /**
     * URL para Obter o "Hello" do Usuario
     */
    public static final String URL_HELLO = "https://fourtonfish.com/hellosalut";

    /**
     * Parametro para Selecionar quais Informações serão Obtidas do IP do Usuario
     *
     * @see #URL_DATA_IP
     */
    public static final String PARAMETER_FIELD_INFO_IP = "fields";
    /**
     * Define quais Informações serão Obtidas do IP do Usuario
     *
     * @see #URL_DATA_IP
     */
    public static final String ATTR_FIELD_INFO_IP = "status,message,country,countryCode,region,regionCode," +
            "regionName,city,timezone,org,mobile,query";
    /**
     * Parametro para Selecionar qual Idioma será mostrado o "Hello"
     *
     * @see #URL_HELLO
     */
    public static final String PARAMETER_IDIOM_HELLO = "lang";
    /**
     * Parametro para Utilizar o IP do Usuario para Obter o "Hello"
     *
     * @see #URL_HELLO
     */
    public static final String PARAMETER_IP_HELLO = "ip";

    /**
     * Realiza uma Consulta em uma URL
     *
     * @return {@link String JSON (String)}|null
     */
    public static String searchByUrl(Uri uri, String method) {
        String result_search = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url_web = new URL(uri.toString());

            // Configura a Conexão com a Internet
            connection = (HttpURLConnection) url_web.openConnection();
            connection.setRequestMethod(method);
            connection.connect();

            // Caso obtenha o Codigo de "Bem-Sucedido", obtem as Informações (Para esse APP, apenas espera a Resposta 200)
            if (connection.getResponseCode() == 200) {

                // Obtem e Verifica o InputSream
                InputStream inputStream = connection.getInputStream();
                if (inputStream != null) {

                    // Obtem uma Leitura do Resultado da Internet
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    // Irão Recuperar os Resultados
                    StringBuilder bufferResponse = new StringBuilder();
                    String line;

                    //Loop para a Leitura Linha por Linha
                    while ((line = reader.readLine()) != null) {
                        bufferResponse.append(line);
                    }

                    // Obtem as Informações do Buffer caso o existam
                    if (bufferResponse.length() != 0) result_search = bufferResponse.toString();
                }
            }
        } catch (Exception e) {
            Log.e("ERROR SEARCH", "Error in Search URL\n" + e.getClass().getName());
            e.printStackTrace();
        } finally {
            //Fecha a Conexão
            if (connection != null) connection.disconnect();
            //Fecha o Reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result_search;
    }

}
