package com.example.mangosapp;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangosapp.Model.Transactions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StrategyFragment extends Fragment {

    //Floating button
    private FloatingActionButton fab_main_btn;
    private ProgressBar progressBar;
    TextView textViewProgressPercentage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_strategy, container, false);

        //Connect floating button to layout
        fab_main_btn=myview.findViewById(R.id.fb_main_plus_btn);


        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsDataInsert();
            }
        });

        // Progress Bar
        /*progress bar
        progressBar=myview.findViewById(R.id.progress_bar);
        textViewProgressPercentage=myview.findViewById(R.id.textViewProgressPercentage);

        int progressoAtual = 50;  // Altere para o valor atual de progresso
        int valorTotal = 100;     // Altere para o valor total

        // Atualize o progresso na barra de progresso
        progressBar.setProgress(progressoAtual);

        // Calcule a porcentagem de progresso
        int porcentagem = (int) (((float) progressoAtual / valorTotal) * 100);

        // Atualize o TextView com a porcentagem
        textViewProgressPercentage.setText(porcentagem + "%");*/


        return myview;
    }
    public void goalsDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.insert_goals_item, null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        Button btnSave=myview.findViewById(R.id.btn_save);
        Button btnCancel=myview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}