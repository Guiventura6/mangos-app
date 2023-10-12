package com.example.mangosapp;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //Floating button
    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_ganhos_btn;
    private FloatingActionButton fab_gastos_btn;

    // Floating button textview..
    private TextView fab_ganhos_txt;
    private TextView fab_gastos_txt;

    //boolen
    private boolean isOpen=false;

    //Animation.
    private Animation FadeOpen, FadeClose;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private static final String ARG_PARAM1 = "param1";
    // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // private String mParam1;
    // private String mParam2;

    //public HomeFragment() {
        // Required empty public constructor
    // }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * // @param param1 Parameter 1.
     * // @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    /* public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

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

                String tmDescription=description.getText().toString().trim();
                // String tmType=type.getText().toString().trim();
                // String tmNote=note.getText().toString().trim();

                if (TextUtils.isEmpty(tmDescription)){
                    description.setError("Required Field..");
                    return;
                }

                /*
                int inamount=Integer.parseInt();

                if (TextUtils.isEmpty(tmType)){
                    type.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(tmNote)){
                    note.setError("Required Field..");
                    return;
                }*/

                // String id=mExpenseDatabase.push().getKey();
                // String mDate= DateFormat.getInstance().format(new Date());

                // Data data=new Data(inamount, tmType, tmNote, id, mDate);
                // mExpenseDatabase.child(id).setValue(data);
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

                String tmDescription=description.getText().toString().trim();
                // String tmType=type.getText().toString().trim();
                // String tmNote=note.getText().toString().trim();

                if (TextUtils.isEmpty(tmDescription)){
                    description.setError("Required Field..");
                    return;
                }

                /*
                int inamount=Integer.parseInt();

                if (TextUtils.isEmpty(tmType)){
                    type.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(tmNote)){
                    note.setError("Required Field..");
                    return;
                }*/

                // String id=mExpenseDatabase.push().getKey();
                // String mDate= DateFormat.getInstance().format(new Date());

                // Data data=new Data(inamount, tmType, tmNote, id, mDate);
                // mExpenseDatabase.child(id).setValue(data);
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