package com.example.hellopeople.entity;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.util.Log;

import com.example.hellopeople.R;
import com.example.hellopeople.utils.Resources;
import com.example.hellopeople.utils.SearchInternet;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Hello {
    private String codeLanguage = "";
    private String language = "";
    private String hello = "";

    // Contrutor Vazio
    public Hello() {
    }

    /**
     * Obtem um JSON do "Hello" no Idioma Especifico ou Pelo IP do Usuario (Automatico)
     * <p>
     * * É Necessario passar o IP ou o Idiom
     *
     * @return {@link String (JSON)}|null
     */
    public static Hello getHelloAPI(Context context, ExecutorService executorService, String ip, String idiom) {
        Hello hello = null;
        try {
            final boolean emptyIdiom = Resources.stringIsNullOrEmpty(idiom);
            String param = emptyIdiom ? SearchInternet.PARAMETER_IP_HELLO : SearchInternet.PARAMETER_IDIOM_HELLO;
            String attr = emptyIdiom ? ip : idiom;

            Uri uriBuild = Uri.parse(SearchInternet.URL_HELLO).buildUpon()
                    .appendQueryParameter(param, attr).build();

            // Configura a Execução da Tarefa Assincrona
            Set<Callable<String>> callableTasksAPI = new HashSet<>();
            callableTasksAPI.add(() -> SearchInternet.searchByUrl(uriBuild, "GET"));

            // Executa as Tarefas Assincronas
            List<Future<String>> futureTasksList = executorService.invokeAll(callableTasksAPI);
            String jsonHello = futureTasksList.get(0).get();

            if (!Resources.stringIsNullOrEmpty(jsonHello)) {
                JSONObject jsonObject = new JSONObject(jsonHello);

                hello = new Hello();
                hello.setHello(jsonObject.isNull("hello")
                        ? ""
                        : Html.fromHtml(jsonObject.getString("hello")).toString());

                if (!emptyIdiom) {
                    hello.setLanguage(idiom);
                } else if (!jsonObject.isNull("code")) {
                    String code = jsonObject.getString("code");

                    if (!code.equals("none")) {
                        hello.setCodeLanguage(code);

                        List<String> code_languages = Arrays.asList(
                                context.getResources().getStringArray(R.array.code_language));

                        int index = code_languages.indexOf(hello.getCodeLanguage());

                        if (index != -1) {
                            List<String> languages = Arrays.asList(
                                    context.getResources().getStringArray(R.array.language));
                            // Obtem a Lista de Idiomas e o Item na Posição encontrada
                            hello.setLanguage(languages.get(index));
                        }
                    } else {
                        hello.setCodeLanguage("Not Recognized");
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR JSON", "Error in JSON\n" + ex.getClass().getName());
            ex.printStackTrace();
        }

        return hello;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(String codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

}
