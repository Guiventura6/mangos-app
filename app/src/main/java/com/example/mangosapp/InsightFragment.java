package com.example.mangosapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsightFragment extends Fragment {

    // Conect with Firebase
    private DatabaseReference mTransactionDatabase;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_insight, container, false);

        // Obtenha uma referência ao gráfico de pizza no layout
        //PieChart pieChart = myview.findViewById(R.id.pieChart);

        // Obtenha uma referência ao gráfico de barras no layout
        BarChart barChart = myview.findViewById(R.id.barChart);

        // Firebase Database
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser= mAuth.getCurrentUser();
        String uid=mUser.getUid();

        mTransactionDatabase = FirebaseDatabase.getInstance().getReference().child("Transactions").child(uid);

        // Buscando gastos por categoria Gráfico de Pizza
        /*
        mTransactionDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Double> categoriaMontanteMap = new HashMap<>();

                for (DataSnapshot movimentacaoSnapshot : snapshot.getChildren()) {
                    String type = movimentacaoSnapshot.child("type").getValue(String.class);

                    if (type != null && type.equals("gasto")) {
                        String category = movimentacaoSnapshot.child("category").getValue(String.class);
                        Double amount = movimentacaoSnapshot.child("amount").getValue(Double.class);

                        if (category != null && amount != null) {
                            // Verifica se a categoria já está no mapa e adiciona o montante
                            if (categoriaMontanteMap.containsKey(category)) {
                                categoriaMontanteMap.put(category, categoriaMontanteMap.get(category) + amount);
                            } else {
                                categoriaMontanteMap.put(category, amount);
                            }
                        }
                    }
                }

                // Crie uma lista de entradas a partir do mapa consolidado
                List<PieEntry> entries = new ArrayList<>();
                for (Map.Entry<String, Double> entry : categoriaMontanteMap.entrySet()) {
                    entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
                }

                // Crie um conjunto de dados com as entradas
                PieDataSet dataSet = new PieDataSet(entries, "Gastos por Categoria");

                // Configuração das cores
                dataSet.setColors(Color.rgb(200, 50, 50), Color.rgb(50, 200, 50), Color.rgb(50, 50, 200));

                // Crie o objeto de dados para o gráfico
                PieData pieData = new PieData(dataSet);

                // Configure o formato dos valores
                pieData.setValueTextSize(12f);
                pieData.setValueTextColor(Color.WHITE);

                // Configure o objeto de dados no gráfico
                pieChart.setData(pieData);

                // Atualize o gráfico
                pieChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lidar com erros
            }
        });
        */


        //Buscando gastos por categoria Gráfico de Barras
        mTransactionDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Double> categoriaMontanteMap = new HashMap<>();

                for (DataSnapshot movimentacaoSnapshot : snapshot.getChildren()) {
                    String type = movimentacaoSnapshot.child("type").getValue(String.class);

                    if (type != null && type.equals("gasto")) {
                        String category = movimentacaoSnapshot.child("category").getValue(String.class);
                        Double amount = movimentacaoSnapshot.child("amount").getValue(Double.class);

                        if (category != null && amount != null) {
                            // Verifica se a categoria já está no mapa e adiciona o montante
                            if (categoriaMontanteMap.containsKey(category)) {
                                categoriaMontanteMap.put(category, categoriaMontanteMap.get(category) + amount);
                            } else {
                                categoriaMontanteMap.put(category, amount);
                            }
                        }
                    }
                }

                // Crie uma lista de entradas a partir do mapa consolidado
                List<BarEntry> entries = new ArrayList<>();
                List<String> labels = new ArrayList<>(); // Lista de rótulos

                int i = 0;
                for (Map.Entry<String, Double> entry : categoriaMontanteMap.entrySet()) {
                    labels.add(entry.getKey()); // Adicione o rótulo da categoria
                    entries.add(new BarEntry(i, entry.getValue().floatValue()));
                    i++;
                }
                // Crie um conjunto de dados com as entradas e aplique as cores personalizadas
                BarDataSet dataSet = new BarDataSet(entries, "Gastos por Categoria");
                // Configuração das cores
                if (getContext() != null) {
                    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                }

                // Remova os valores do lado direito do gráfico
                dataSet.setDrawValues(false);

                // Crie o objeto de dados para o gráfico de barras
                BarData barData = new BarData(dataSet);

                // Configure o objeto de dados no gráfico de barras
                barChart.setData(barData);

                // Configure a descrição do gráfico (opcional)
                barChart.getDescription().setEnabled(false);

                // Configure os rótulos (categorias) nas barras
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setGranularity(1);

                // Remova os valores do lado direito do eixo Y
                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setDrawAxisLine(true);
                leftAxis.setDrawLabels(true);

                // Remova o eixo Y do lado direito
                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setEnabled(false);

                // Atualize o gráfico de barras
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lidar com erros
            }
        });

        return myview;
    }
}