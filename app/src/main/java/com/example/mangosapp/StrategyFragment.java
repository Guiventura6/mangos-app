package com.example.mangosapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class StrategyFragment extends Fragment {

    //Floating button
    private FloatingActionButton fab_main_btn;

    // Conecte Firebase
    private DatabaseReference mGoalsDatabase;

    //Recycler view
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;

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

        //Conect Recycler
        recyclerView=myview.findViewById(R.id.recycler_id_strategy);

        //Recycler config
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsDataInsert();
            }
        });


        return myview;
    }

    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Goals> options= new FirebaseRecyclerOptions.Builder<Goals>()
                .setQuery(mGoalsDatabase, Goals.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Goals, StrategyFragment.GoalsViewHolder>(options){

            @NonNull
            @Override
            public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new GoalsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_recycler_data, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull GoalsViewHolder holder, int position, @NonNull Goals model) {
                holder.setGoalsTitle(model.getTitle());
                holder.setGoalsReason(model.getReason());
                holder.setGoalsTotalAmount(model.getTotal_amount());
                holder.setGoalsDeadline(model.getDeadline());
                holder.setGoalsCurrentAmount(model.getCurrent_amount());
                holder.setGoalsProgressBar(model.getCurrent_amount(), model.getTotal_amount());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private static class GoalsViewHolder extends RecyclerView.ViewHolder{

        View mGastosView;

        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);
            mGastosView=itemView;
        }

        void setGoalsTitle(String title){
            TextView mTitle=mGastosView.findViewById(R.id.title_txt_goals);
            mTitle.setText(title);
        }

        void setGoalsReason(String reason){
            TextView mReason=mGastosView.findViewById(R.id.reason_txt_goals);
            mReason.setText(reason);
        }

        void setGoalsTotalAmount(int amount){
            TextView mTotalAmount=mGastosView.findViewById(R.id.total_amount_txt_goals);
            String strAmount=String.valueOf(amount);
            mTotalAmount.setText("R$ " + strAmount+ ",00");
        }

        void setGoalsDeadline(String date){
            TextView mDeadLine=mGastosView.findViewById(R.id.deadline_txt_goals);
            mDeadLine.setText(date);
        }

        void setGoalsCurrentAmount(int amount){
            TextView mCurrentAmount=mGastosView.findViewById(R.id.current_amount_txt_goals);
            String strCurrentAmount=String.valueOf(amount);
            mCurrentAmount.setText("R$ " + strCurrentAmount+ ",00");
        }

        void setGoalsProgressBar(int current_amount, int total_amount){
            // Progress Bar
            ProgressBar progressBar = mGastosView.findViewById(R.id.progress_bar);
            TextView progressPercentage=mGastosView.findViewById(R.id.progress_percentage_txt_goals);

            int progressoAtual = current_amount;
            int valorTotal = total_amount;

            // Calcule a porcentagem de progresso
            int porcentagem = (int) (((float) progressoAtual / valorTotal) * 100);

            // Atualize o TextView com a porcentagem
            progressPercentage.setText(porcentagem + "%");

            // Atualize o progresso na barra de progresso
            progressBar.setProgress(porcentagem);
        }

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
        final EditText total_amount=myview.findViewById(R.id.total_amount_edt);
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
                int intCurrentAmount=Integer.parseInt(txtCurrentAmount);


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

    /*
    public void updateGoalItem() {

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.insert_goals_item, null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        final EditText title=myview.findViewById(R.id.title_edt);
        final EditText reason=myview.findViewById(R.id.reason_edt);
        final EditText total_amount=myview.findViewById(R.id.total_amount_edt);
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
                int intCurrentAmount=Integer.parseInt(txtCurrentAmount);


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
    */
}