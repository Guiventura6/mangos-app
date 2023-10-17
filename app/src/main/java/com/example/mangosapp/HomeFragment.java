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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangosapp.Model.Transactions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //Floating button
    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_ganhos_btn;
    private FloatingActionButton fab_gastos_btn;

    // Floating button textview..
    private TextView fab_ganhos_txt;
    private TextView fab_gastos_txt;

    // Total saldo, gastos, ganhos
    private TextView total_saldo;
    private TextView total_gastos;
    private TextView total_ganhos;

    //boolen
    private boolean isOpen=false;

    //Animation.
    private Animation FadeOpen, FadeClose;

    // Conecte Firebase
    private DatabaseReference mTransactionDatabase;

    //Recycler view
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;

    private static final int VIEW_TYPE_GASTO = 1;
    private static final int VIEW_TYPE_GANHO = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_home, container, false);

        FadeOpen= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose=AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        //Connect floating button to layout
        fab_main_btn=myview.findViewById(R.id.fb_main_plus_btn);
        fab_ganhos_btn=myview.findViewById(R.id.ganhos_ft_btn);
        fab_gastos_btn=myview.findViewById(R.id.gastos_ft_btn);

        // Connect floating text
        fab_ganhos_txt=myview.findViewById(R.id.income_ft_text);
        fab_gastos_txt=myview.findViewById(R.id.expense_ft_text);

        // Total saldo, gastos e ganhos
        total_saldo=myview.findViewById(R.id.home_txt_saldo_total);
        total_gastos=myview.findViewById(R.id.home_txt_saldo_gastos);
        total_ganhos=myview.findViewById(R.id.home_txt_saldo_ganhos);

        // Firebase Database
        mTransactionDatabase = FirebaseDatabase.getInstance().getReference().child("Transactions");

        //Conect Recycler
        recyclerView=myview.findViewById(R.id.recycler_id_home);

        //Recycler config
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //Calculate saldos
        mTransactionDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int saldo=0;
                int gastos=0;
                int ganhos=0;

                for (DataSnapshot mysnap:snapshot.getChildren()){
                    Transactions data=mysnap.getValue(Transactions.class);

                    if (data.getType().equals("gasto")){
                        gastos+=data.getAmount();
                    } else if (data.getType().equals("ganho")) {
                        ganhos+= data.getAmount();
                    }
                }

                String str_gastos=String.valueOf(gastos);
                String str_ganhos=String.valueOf(ganhos);
                total_gastos.setText("-R$ "+str_gastos+",00");
                total_ganhos.setText("+R$ "+str_ganhos+",00");

                saldo = ganhos - gastos;
                String str_saldo=String.valueOf(saldo);
                if (saldo < 0){
                    total_saldo.setText("-R$ "+str_saldo+",00");
                } else {
                    total_saldo.setText("+R$ "+str_saldo+",00");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if (isOpen){
                    fab_ganhos_btn.startAnimation(FadeClose);
                    fab_gastos_btn.startAnimation(FadeClose);
                    fab_ganhos_btn.setClickable(false);
                    fab_gastos_btn.setClickable(false);

                    fab_ganhos_txt.startAnimation(FadeClose);
                    fab_gastos_txt.startAnimation(FadeClose);
                    fab_ganhos_txt.setClickable(false);
                    fab_gastos_txt.setClickable(false);
                    isOpen=false;
                }else {
                    fab_ganhos_btn.startAnimation(FadeOpen);
                    fab_gastos_btn.startAnimation(FadeOpen);
                    fab_ganhos_btn.setClickable(true);
                    fab_gastos_btn.setClickable(true);

                    fab_ganhos_txt.startAnimation(FadeOpen);
                    fab_gastos_txt.startAnimation(FadeOpen);
                    fab_ganhos_txt.setClickable(true);
                    fab_ganhos_txt.setClickable(true);
                    isOpen=true;
                }
            }
        });

        return myview;
    }


    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Transactions> options= new FirebaseRecyclerOptions.Builder<Transactions>()
                .setQuery(mTransactionDatabase, Transactions.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Transactions, RecyclerView.ViewHolder>(options){

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                if (viewType == VIEW_TYPE_GASTO) {
                    view = inflater.inflate(R.layout.gastos_recycler_data, parent, false);
                    return new GastosViewHolder(view);
                } else { // VIEW_TYPE_GANHO
                    view = inflater.inflate(R.layout.ganhos_recycler_data, parent, false);
                    return new GanhosViewHolder(view);
                }
            }

            @Override
            public int getItemViewType(int position) {
                // Retorne um valor único para cada tipo de item (gasto ou ganho)
                return getItem(position).getType().equals("gasto") ? VIEW_TYPE_GASTO : VIEW_TYPE_GANHO;
            }


            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Transactions model) {
                if (model.getType().equals("gasto")) {
                    ((GastosViewHolder) holder).setGastosDescription(model.getDescription());
                    ((GastosViewHolder) holder).setGastosAmount(model.getAmount());
                    ((GastosViewHolder) holder).setGastosDate(model.getDate());
                    ((GastosViewHolder) holder).setGastosCategory(model.getCategory());
                } else if (model.getType().equals("ganho")) {
                    ((GanhosViewHolder) holder).setGanhosDescription(model.getDescription());
                    ((GanhosViewHolder) holder).setGanhosAmount(model.getAmount());
                    ((GanhosViewHolder) holder).setGanhosDate(model.getDate());
                    ((GanhosViewHolder) holder).setGanhosCategory(model.getCategory());
                }
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private static class GastosViewHolder extends RecyclerView.ViewHolder{

        View mGastosView;

        public GastosViewHolder(@NonNull View itemView) {
            super(itemView);
            mGastosView=itemView;
        }

        void setGastosDescription(String description){
            TextView mDescription=mGastosView.findViewById(R.id.description_txt_gastos);
            mDescription.setText(description);
        }

        void setGastosAmount(int amount){
            TextView mAmount=mGastosView.findViewById(R.id.amount_txt_gastos);
            String strAmount=String.valueOf(amount);
            mAmount.setText("-R$ " + strAmount+ ",00");
        }

        void setGastosDate(String date){
            TextView mDate=mGastosView.findViewById(R.id.data_txt_gastos);
            mDate.setText(date);
        }

        void setGastosCategory(String category){
            TextView mCategory=mGastosView.findViewById(R.id.category_txt_gastos);
            mCategory.setText(category);
        }

    }

    private static class GanhosViewHolder extends RecyclerView.ViewHolder{

        View mGanhosView;

        public GanhosViewHolder(@NonNull View itemView) {
            super(itemView);
            mGanhosView=itemView;
        }

        void setGanhosDescription(String description){
            TextView mDescription=mGanhosView.findViewById(R.id.description_txt_ganhos);
            mDescription.setText(description);
        }

        void setGanhosAmount(int amount){
            TextView mAmount=mGanhosView.findViewById(R.id.amount_txt_ganhos);
            String strAmount=String.valueOf(amount);
            mAmount.setText("+R$ " + strAmount+ ",00");
        }

        void setGanhosDate(String date){
            TextView mDate=mGanhosView.findViewById(R.id.date_txt_ganhos);
            mDate.setText(date);
        }

        void setGanhosCategory(String category){
            TextView mCategory=mGanhosView.findViewById(R.id.category_txt_ganhos);
            mCategory.setText(category);
        }

    }


    //Floating button animation
    private void ftAnimation(){
        if (isOpen){

            fab_ganhos_btn.startAnimation(FadeClose);
            fab_gastos_btn.startAnimation(FadeClose);
            fab_ganhos_btn.setClickable(false);
            fab_gastos_btn.setClickable(false);

            fab_ganhos_txt.startAnimation(FadeClose);
            fab_gastos_txt.startAnimation(FadeClose);
            fab_ganhos_txt.setClickable(false);
            fab_gastos_txt.setClickable(false);
            isOpen=false;
        }else {
            fab_ganhos_btn.startAnimation(FadeOpen);
            fab_gastos_btn.startAnimation(FadeOpen);
            fab_ganhos_btn.setClickable(true);
            fab_gastos_btn.setClickable(true);

            fab_ganhos_txt.startAnimation(FadeOpen);
            fab_gastos_txt.startAnimation(FadeOpen);
            fab_ganhos_txt.setClickable(true);
            fab_ganhos_txt.setClickable(true);
            isOpen=true;
        }
    }

    private void addData(){

        //Fab Button income...
        fab_ganhos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganhosDataInsert();
            }
        });

        fab_gastos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gastosDataInsert();
            }
        });

    }

    public void gastosDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.insert_gastos_item, null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        final EditText description=myview.findViewById(R.id.description_edt);
        final EditText amount=myview.findViewById(R.id.amount_edt);
        final EditText data=myview.findViewById(R.id.data_edt);
        final EditText category=myview.findViewById(R.id.category_edt);

        Button btnSave=myview.findViewById(R.id.btn_save);
        Button btnCancel=myview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtDescription=description.getText().toString().trim();
                String txtAmount=amount.getText().toString().trim();
                String txtData=data.getText().toString().trim();
                String txtCategory=category.getText().toString().trim();

                if (TextUtils.isEmpty(txtDescription)){
                    description.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(txtAmount)){
                    amount.setError("Required Field..");
                    return;
                }

                int intAmount=Integer.parseInt(txtAmount);

                if (TextUtils.isEmpty(txtData)){
                    data.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(txtCategory)){
                    category.setError("Required Field..");
                    return;
                }

                String id= mTransactionDatabase.push().getKey();
                String type="gasto";
                // String mDate= DateFormat.getInstance().format(new Date());

                Transactions data=new Transactions(id, txtDescription, intAmount, txtData, txtCategory, type);

                mTransactionDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "Dados Adicionados", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    public void ganhosDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.insert_ganhos_item, null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        final EditText description=myview.findViewById(R.id.description_edt);
        final EditText amount=myview.findViewById(R.id.amount_edt);
        final EditText data=myview.findViewById(R.id.data_edt);
        final EditText category=myview.findViewById(R.id.category_edt);

        Button btnSave=myview.findViewById(R.id.btn_save);
        Button btnCancel=myview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtDescription=description.getText().toString().trim();
                String txtAmount=amount.getText().toString().trim();
                String txtData=data.getText().toString().trim();
                String txtCategory=category.getText().toString().trim();

                if (TextUtils.isEmpty(txtDescription)){
                    description.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(txtAmount)){
                    amount.setError("Required Field..");
                    return;
                }

                int intAmount=Integer.parseInt(txtAmount);

                if (TextUtils.isEmpty(txtData)){
                    data.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(txtCategory)) {
                    category.setError("Required Field..");
                    return;
                }

                String id= mTransactionDatabase.push().getKey();
                String type="ganho";

                Transactions data=new Transactions(id, txtDescription, intAmount, txtData, txtCategory, type);

                mTransactionDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "Dados Adicionados", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}