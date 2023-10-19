package com.example.mangosapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsightFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsightFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_insight, container, false);

        // Obtenha uma referência ao gráfico de pizza no layout
        PieChart pieChart = myview.findViewById(R.id.pieChart);

        // Crie uma lista de entradas para o gráfico de pizza (categorias e seus gastos)
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, "Alimentação"));
        entries.add(new PieEntry(20f, "Transporte"));
        entries.add(new PieEntry(50f, "Outros"));

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

        // Adicione uma descrição para o gráfico (opcional)
        pieChart.getDescription().setEnabled(false);

        // Atualize o gráfico
        pieChart.invalidate();

        return myview;
    }
}