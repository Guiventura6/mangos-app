package com.example.mangosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        // Recupera o extra enviado pela MainActivity
        String conteudo = getIntent().getStringExtra("conteudo");

        // Carrega o Fragmento correspondente ao subtema clicado
        Fragment fragment = getFragmentForSubtema(conteudo);

        if (fragment != null) {
            // Substitui o conteúdo do contêiner pelo Fragmento
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private Fragment getFragmentForSubtema(String subtema) {
        switch (subtema) {
            case "Receitas e Despesas":
                return new subtema1();
            case "Juros e inflação":
                return new subtema2();
            case "Créditos":
                return new subtema3();
            case "Investimentos":
                return new subtema4();
            case "Contas":
                return new subtema5();
            default:
                // Pode retornar um Fragmento padrão ou null, dependendo dos requisitos
                return null;
        }
    }
}
