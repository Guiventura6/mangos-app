package com.example.mangosapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EducationFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_education, container, false);

        expandableListView = rootView.findViewById(R.id.expandableListView);

        // Prepare data for the ExpandableListView
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // Set the adapter to the ExpandableListView
        expandableListView.setAdapter(listAdapter);

        return rootView;
    }

    // Prepare the data for the ExpandableListView
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Add themes (headers) and related topics (child items)
        listDataHeader.add("Fundamentos");
        listDataHeader.add("Intermediário");
        listDataHeader.add("Avançado");

        List<String> fundamentos = new ArrayList<>();
        fundamentos.add("Básico");
        fundamentos.add("Intermediário");
        fundamentos.add("Avançado");

        List<String> intermediario = new ArrayList<>();
        intermediario.add("Tópico 1");
        intermediario.add("Tópico 2");
        intermediario.add("Tópico 3");

        List<String> avancado = new ArrayList<>();
        avancado.add("Tópico 1");
        avancado.add("Tópico 2");
        avancado.add("Tópico 3");

        listDataChild.put(listDataHeader.get(0), fundamentos);
        listDataChild.put(listDataHeader.get(1), intermediario);
        listDataChild.put(listDataHeader.get(2), avancado);
    }
}
