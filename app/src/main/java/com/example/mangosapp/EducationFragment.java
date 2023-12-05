package com.example.mangosapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
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
        listDataHeader.add("Conceitos Básicos");
        listDataHeader.add("Atravesse a Crise");
        listDataHeader.add("Cuide do seu dinheiro");
        listDataHeader.add("Saia do Vermelho");
        listDataHeader.add("Planeje o Futuro");
        listDataHeader.add("Hábitos Financeiros e Reflexões");

        List<String> principios_das_financas = new ArrayList<>();
        principios_das_financas.add("Receitas e Despesas");
        principios_das_financas.add("Juros e inflação");
        principios_das_financas.add("Créditos");
        principios_das_financas.add("Investimentos");
        principios_das_financas.add("Contas");

        List<String> poupanca = new ArrayList<>();
        poupanca.add("Diferença entre desejo e necessidade");
        poupanca.add("Descobrindo seu equilíbrio");
        poupanca.add("Liste os itens essenciais antes de ir às compras");
        poupanca.add("Cuidado com as propagandas");
        poupanca.add("Proteja seu dinheiro");

        List<String> livre_se_das_dividas = new ArrayList<>();
        livre_se_das_dividas.add("Organize seu orçamento");
        livre_se_das_dividas.add("Fazendo seu orçamento na práticar");
        livre_se_das_dividas.add("Estratégias para economizar, gastando melhor");
        livre_se_das_dividas.add("Ganhar mais significa poder gastar mais?");
        livre_se_das_dividas.add("Como se preparar para imprevistos?");


        List<String> negocios_e_investimentos = new ArrayList<>();
        negocios_e_investimentos.add("Conheça o tamanho da sua dívida");
        negocios_e_investimentos.add("Como retomar o controle");
        negocios_e_investimentos.add("Faça um plano para pagamento");
        negocios_e_investimentos.add("Diminua gastos e tente ter uma renda extra");
        negocios_e_investimentos.add("Use as opções de crédito a seu favor");

        List<String> desenvolvimento_pessoal = new ArrayList<>();
        desenvolvimento_pessoal.add("Como planejar");
        desenvolvimento_pessoal.add("Estabeleça objetivos de curto, médio e longo prazo");
        desenvolvimento_pessoal.add("Como alcançar suas metas");
        desenvolvimento_pessoal.add("Estilo de vida e riscos de endividamento");
        desenvolvimento_pessoal.add("Rumo à cidadania financeira");


        List<String> habitos_financeiros_e_reflexoes = new ArrayList<>();
        habitos_financeiros_e_reflexoes.add("Bons Hábitos");
        habitos_financeiros_e_reflexoes.add("Maus Hábitos: Como mudar seus hábitos");
        habitos_financeiros_e_reflexoes.add("Como mudar seus hábitos");
        habitos_financeiros_e_reflexoes.add("Reflexões");
        habitos_financeiros_e_reflexoes.add("Minimalismo");
        habitos_financeiros_e_reflexoes.add("Vida de Sucesso");


        listDataChild.put(listDataHeader.get(0), principios_das_financas);
        listDataChild.put(listDataHeader.get(1), poupanca);
        listDataChild.put(listDataHeader.get(2), livre_se_das_dividas);
        listDataChild.put(listDataHeader.get(3), negocios_e_investimentos);
        listDataChild.put(listDataHeader.get(4), desenvolvimento_pessoal);
        listDataChild.put(listDataHeader.get(5), habitos_financeiros_e_reflexoes);
    }
}
