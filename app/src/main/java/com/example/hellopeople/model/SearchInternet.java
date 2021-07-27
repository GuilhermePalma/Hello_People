package com.example.hellopeople.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchInternet {

    public static String searchByUrl(String url, String method) {

        String result_search;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url_web = new URL(url);

            // Configura a Conexão com a Internet
            connection = (HttpURLConnection) url_web.openConnection();
            connection.setRequestMethod(method);
            connection.connect();

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

                //Buffer sem Informações
                if (bufferResponse.length() == 0) return "";

                result_search = bufferResponse.toString();

            } else return "";

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("ERROR FORMED URL", "Error in URL Formation\n" + e);
            return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("NO DATA AVAILABLE", "Error recovery Data\n" + e);
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR READ URL", "Error reading URL Connection\n" + e);
            return "";
        } finally {
            //Fecha a Conexão
            if (connection != null) {
                connection.disconnect();
            }
            //Fecha o Reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.i("RESULT SEARCH", result_search);

        return result_search;

    }


}
