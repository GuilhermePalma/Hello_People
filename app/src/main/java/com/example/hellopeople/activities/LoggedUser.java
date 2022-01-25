package com.example.hellopeople.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.hellopeople.entity.Hello;
import com.example.hellopeople.entity.Ip;
import com.example.hellopeople.entity.User;
import com.example.hellopeople.utils.ManagerSharedPreferences;
import com.example.hellopeople.utils.Resources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggedUser extends AppCompatActivity {

    private LinearLayout layout_more;
    private Button button_more;
    private TextView txt_logged;
    private TextView txt_goodDay;
    private TextView txt_ip;
    private User user;
    private ManagerSharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user);

        // Instancia os Itens
        instanceItems();

        // Obtem os Dados (IP e seus Detalhes, "Hello" Traduzido) das APIs
        searchAsyncInternet();

        // Listener dos dois botões Inferiores
        listenerLogout();
        listenerSeeMore();
    }

    /**
     * Instancia os Itens que serão Usados na Classe
     */
    private void instanceItems() {
        button_more = findViewById(R.id.btn_more);
        txt_logged = findViewById(R.id.text_logged);
        txt_goodDay = findViewById(R.id.text_goodday);
        preferences = new ManagerSharedPreferences(LoggedUser.this);
        user = preferences.getUserPreferences();
    }

    /**
     * Listener do Botão "Logout". Finaliza o Usuario ativo e Exclui o Arquivo das SharedPreferences
     */
    private void listenerLogout() {
        Button button_logout = findViewById(R.id.btn_logout);
        button_logout.setOnClickListener(v -> {
            preferences.resetSharedPreferences();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void listenerSeeMore() {
        layout_more = findViewById(R.id.layout_more);
        button_more.setOnClickListener(v -> {
            if (layout_more.getVisibility() == View.VISIBLE) {
                button_more.setText(R.string.btn_moreDefault);
                layout_more.setVisibility(View.GONE);
            } else {
                button_more.setText(R.string.btn_moreAfterClick);
                layout_more.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Reaçiza a Busca Assincrona nas APIs, obtendo o IP, Datalhes do IP e o "Hello" no Idioma e
     * exibe esses dados na Tela
     *
     * @see #showDetailsIp(Ip)
     * @see #showMessageUser(Hello)
     */
    private void searchAsyncInternet() {
        // Obtem e Configura a Progress Bar que será Exibida
        ProgressBar process_loading = findViewById(R.id.progressBar_loading);
        TextView txt_search = findViewById(R.id.text_search);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> {

            final String ipUser = Ip.getAddressIP(executor);

            Ip ip = Ip.getDetailsIP(executor, ipUser);
            if (ip == null) {
                runOnUiThread(() -> {
                    showDetailsIp(null);
                    process_loading.setVisibility(View.GONE);
                    txt_search.setText(R.string.error_search);
                    Toast.makeText(this, R.string.message_noIp, Toast.LENGTH_LONG)
                            .show();
                });
                return;
            }

            runOnUiThread(() -> {
                // Show Ip Address
                txt_ip = findViewById(R.id.text_ip);
                txt_ip.setText(String.format(getString(R.string.txt_ip), ipUser));
                showDetailsIp(ip);
            });

            // Obtem o "Hello" pelo IP ou Pelo Idioma Selecionado
            String codeLanguage = user.getCode_language();
            final Hello hello = Hello.getHelloAPI(LoggedUser.this, executor,
                    Resources.stringIsNullOrEmpty(codeLanguage) ? ipUser : null,
                    Resources.stringIsNullOrEmpty(codeLanguage) ? null : codeLanguage);

            if (hello == null) {
                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.error_hello,
                            Toast.LENGTH_LONG).show();

                    Log.e("ERROR HELLO", "Unable to retrieve 'hello' by IP");
                });
            } else runOnUiThread(() -> showMessageUser(hello));

            runOnUiThread(() -> {
                // Remove os Itens do Loading da busca Assincrono
                process_loading.setVisibility(View.GONE);
                txt_search.setVisibility(View.GONE);
                txt_logged.setVisibility(View.VISIBLE);
                txt_goodDay.setVisibility(View.VISIBLE);
                button_more.setVisibility(View.VISIBLE);
            });
        });
    }

    /**
     * Exibe o "Hello" para o Usuario e a uma Mensagem
     */
    private void showMessageUser(Hello hello) {
        TextView txt_language = findViewById(R.id.text_language);
        txt_language.setText(String.format(getString(R.string.txt_langue),
                hello.getCodeLanguage(), hello.getLanguage()));

        boolean isFirstLogin = preferences.isFirstLogin();

        // Configura a Exibição da Mensagem com o "Hello"
        if (isFirstLogin) preferences.setFirstLogin(false);
        txt_logged.setText(Html.fromHtml(
                getString(isFirstLogin ? R.string.message_successLogin : R.string.message_hello,
                        hello.getHello(), user.getName())));


        txt_goodDay.setText(Html.fromHtml(getString(R.string.message_goodDay, user.getName())));
    }

    /**
     * Configura e Exibe os Detalhes do IP do Usuario
     *
     * @param ip {@link Ip} instanciado com os Detalhes que serão mostrados
     */
    private void showDetailsIp(Ip ip) {
        TextView txt_mobile = findViewById(R.id.text_mobile);
        TextView txt_country = findViewById(R.id.text_country);
        TextView txt_region = findViewById(R.id.text_region);
        TextView txt_city = findViewById(R.id.text_city);
        TextView txt_timezone = findViewById(R.id.text_timezone);
        TextView txt_org = findViewById(R.id.text_org);
        TextView txt_errorMessage = findViewById(R.id.text_errorMessage);

        TextView[] textViews = new TextView[]{txt_country, txt_region, txt_city, txt_timezone, txt_org, txt_mobile};

        // Verifica se houve algum erro
        if (ip == null) {
            // Exibe o Erro e Esconde os Detalhes do IP
            txt_errorMessage.setVisibility(View.VISIBLE);
            txt_errorMessage.setText(getString(R.string.txt_errorMessage));

            for (TextView textView : textViews) {
                textView.setVisibility(View.GONE);
            }
        } else {
            // Remove o Erro, caso esteja a mostra
            if (txt_errorMessage.getVisibility() == View.VISIBLE) {
                txt_errorMessage.setVisibility(View.GONE);
            }

            // Exibe os Detalhes do IP (Caso não estejam a mostra)
            for (TextView textView : textViews) {
                if (textView.getVisibility() == View.GONE) {
                    textView.setVisibility(View.VISIBLE);
                }
            }

            txt_country.setText(String.format(
                    getString(R.string.txt_country), ip.getCountry(), ip.getCountry_code()));
            txt_region.setText(String.format(
                    getString(R.string.txt_region), ip.getRegion(), ip.getRegion_code()));
            txt_city.setText(String.format(getString(R.string.txt_city), ip.getCity()));
            txt_timezone.setText(String.format(getString(R.string.txt_timezone), ip.getTimeZone()));
            txt_org.setText(String.format(getString(R.string.txt_organization), ip.getOrg()));
            txt_mobile.setText(String.format(getString(R.string.txt_mobile), ip.isMobile() ? "Yes" : "No"));
        }
    }

}