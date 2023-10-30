package com.example.mangosapp;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangosapp.Model.Goals;
import com.example.mangosapp.Model.Transactions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class StrategyFragment extends Fragment {

    //Floating button
    private FloatingActionButton fab_main_btn;

    // Conecte Firebase
    private DatabaseReference mGoalsDatabase;

    //Recycler view
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;

    //Update and Delete Item Goal
    private String title;
    private String reason;
    private double total_amount;
    private String deadline;
    private double current_amount;
    private String post_key;

    // Selecionando a Data do objetivo
    private EditText editTextDate;

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

                holder.mGastosView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos=holder.getBindingAdapterPosition();
                        post_key=getRef(pos).getKey();

                        title=model.getTitle();
                        reason=model.getReason();
                        total_amount=model.getTotal_amount();
                        deadline=model.getDeadline();
                        current_amount= model.getCurrent_amount();
                        updateGoalItem(post_key);
                    }
                });

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

        void setGoalsTotalAmount(double amount){
            TextView mTotalAmount=mGastosView.findViewById(R.id.total_amount_txt_goals);
            String strAmount=String.valueOf(amount);
            mTotalAmount.setText("R$ " + strAmount);
        }

        void setGoalsDeadline(String date){
            TextView mDeadLine=mGastosView.findViewById(R.id.deadline_txt_goals);
            mDeadLine.setText(date);
        }

        void setGoalsCurrentAmount(double amount){
            TextView mCurrentAmount=mGastosView.findViewById(R.id.current_amount_txt_goals);
            String strCurrentAmount=String.valueOf(amount);
            mCurrentAmount.setText("R$ " + strCurrentAmount);
        }

        void setGoalsProgressBar(double current_amount, double total_amount){
            // Progress Bar
            ProgressBar progressBar = mGastosView.findViewById(R.id.progress_bar);
            TextView progressPercentage=mGastosView.findViewById(R.id.progress_percentage_txt_goals);

            double progressoAtual = current_amount;
            double valorTotal = total_amount;

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

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(data);
            }
        });

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

                double doubleTotalAmount=Double.parseDouble(txtTotalAmount);
                double doubleCurrentAmount=Double.parseDouble(txtCurrentAmount);


                String id= mGoalsDatabase.push().getKey();
                String created= DateFormat.getDateInstance().format(new Date());

                Goals data=new Goals(id, txtTitle, txtReason, created, txtDeadline, doubleCurrentAmount, doubleTotalAmount);

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

    private void showDatePickerDialog(final EditText editTextDate) {

        int year, month, day;

        // Obtém a data atual
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Aqui você pode fazer algo com a data selecionada
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                editTextDate.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void updateGoalItem(String goalId) {

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.update_goal_item, null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();

        final EditText edtTitle=myview.findViewById(R.id.title_edt);
        final EditText edtReason=myview.findViewById(R.id.reason_edt);
        final EditText edtTotal_amount=myview.findViewById(R.id.total_amount_edt);
        final EditText edtDeadline=myview.findViewById(R.id.data_edt);
        final EditText edtCurrent_amount=myview.findViewById(R.id.current_amount_edt);

        Button btnUpdate=myview.findViewById(R.id.btn_update);
        Button btnDelete=myview.findViewById(R.id.btn_deletar);

        //Set data to edit text..
        edtTitle.setText(title);
        edtTitle.setSelection(title.length());

        edtReason.setText(reason);
        edtReason.setSelection(reason.length());

        edtTotal_amount.setText(String.valueOf(total_amount));
        edtTotal_amount.setSelection(String.valueOf(total_amount).length());

        edtDeadline.setText(deadline);
        edtDeadline.setSelection(deadline.length());

        edtCurrent_amount.setText(String.valueOf(current_amount));
        edtCurrent_amount.setSelection(String.valueOf(current_amount).length());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTitle=edtTitle.getText().toString().trim();
                String txtReason=edtReason.getText().toString().trim();
                String txtTotalAmount=edtTotal_amount.getText().toString().trim();
                String txtDeadline=edtDeadline.getText().toString().trim();
                String txtCurrentAmount=edtCurrent_amount.getText().toString().trim();

                if (TextUtils.isEmpty(txtTitle)){
                    edtTitle.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(txtReason)){
                    edtReason.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(txtDeadline)){
                    edtDeadline.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(txtCurrentAmount)){
                    edtCurrent_amount.setError("Required Field..");
                    return;
                }

                double intTotalAmount=Double.parseDouble(txtTotalAmount);
                double intCurrentAmount=Double.parseDouble(txtCurrentAmount);

                DatabaseReference goalRef = mGoalsDatabase.child(goalId);

                // Mantenha a data de criação original
                goalRef.child("title").setValue(txtTitle);
                goalRef.child("reason").setValue(txtReason);
                goalRef.child("total_amount").setValue(intTotalAmount);
                goalRef.child("deadline").setValue(txtDeadline);
                goalRef.child("current_amount").setValue(intCurrentAmount);

                Toast.makeText(getActivity(), "Dados Atualizados", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoalsDatabase.child(goalId).removeValue();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}