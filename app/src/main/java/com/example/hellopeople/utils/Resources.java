package com.example.hellopeople.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Resources {

    /**
     * Verifica se uma Strign é null ou Vazia ("")
     *
     * @return true|false
     */
    public static boolean stringIsNullOrEmpty(String stringCheck) {
        return stringCheck == null || stringCheck.equals("");
    }

    /**
     * Verifica se há Internet Disponivel para ser utilziada
     *
     * @return true|false
     */
    public static boolean hasConnectionAvailable(Context context) {

        // Obtem o Serviço de Conexão
        ConnectivityManager connectionManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;

        // Tenta obter as Infromações da Conexão
        if (connectionManager != null) {
            networkInfo = connectionManager.getActiveNetworkInfo();

            // Verifica se a Internet está Conectada
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                // Connection is not Available
                Log.e("NO CONECTED", "Internet not Connected\nConection Infos: " + networkInfo);
                return false;
            }

        } else {
            Log.e("NO SERVICE", "Error in Connectivity Service (Service = null)");
            return false;
        }
    }
}
