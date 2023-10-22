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

import com.example.mangosapp.Model.Goals;
import com.example.mangosapp.Model.Transactions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class StrategyFragment extends Fragment {

    //Floating button
    private FloatingActionButton fab_main_btn;
    private ProgressBar progressBar;
    TextView textViewProgressPercentage;

    // Conecte Firebase
    private DatabaseReference mGoalsDatabase;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_strategy, container, false);

        //Connect floating button to layout
        fab_main_btn=myview.findViewById(R.id.fb_main_plus_btn);

        // Firebase Database
        mGoalsDatabase = FirebaseDatabase.getInstance().getReference().child("Goals");


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

        final EditText title=myview.findViewById(R.id.title_edt);
        final EditText reason=myview.findViewById(R.id.reason_edt);
        final EditText total_amount=myview.findViewById(R.id.amount_edt);
        final EditText data=myview.findViewById(R.id.data_edt);
        final EditText current_amount=myview.findViewById(R.id.current_amount_edt);

        Button btnSave=myview.findViewById(R.id.btn_save);
        Button btnCancel=myview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTitle=title.getText().toString().trim();
                String txtReason=reason.getText().toString().trim();
                String txtTotalAmount=total_amount.getText().toString().trim();
                String txtDeadline=data.getText().toString().trim();
                String txtCurrentAmount=current_amount.getText().toString().trim();

                if (TextUtils.isEmpty(txtTitle)){
                    title.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(txtReason)){
                    reason.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(txtDeadline)){
                    data.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(txtCurrentAmount)){
                    current_amount.setError("Required Field..");
                    return;
                }

                int intTotalAmount=Integer.parseInt(txtTotalAmount);
                int intCurrentAmount=Integer.parseInt(txtTotalAmount);


                String id= mGoalsDatabase.push().getKey();
                String created= DateFormat.getDateInstance().format(new Date());

                Goals data=new Goals(id, txtTitle, txtReason, created, txtDeadline, intCurrentAmount, intTotalAmount);

                mGoalsDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "Dados Adicionados", Toast.LENGTH_SHORT).show();

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