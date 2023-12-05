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
            case "Compreenda melhor as finanças":
                return new subtema1();
            case "Ganhe mais dinheiro":
                return new subtema2();
            // Adicione mais casos conforme necessário
            default:
                // Pode retornar um Fragmento padrão ou null, dependendo dos requisitos
                return null;
        }
    }
}
