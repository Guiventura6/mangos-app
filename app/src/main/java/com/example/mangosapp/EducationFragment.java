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
        listDataHeader.add("Princípios das Finanças");
        listDataHeader.add("Economia (Poupança)");
        listDataHeader.add("Livre-se das Dívidas");
        listDataHeader.add("Negócios e Investimento");
        listDataHeader.add("Desenvolvimento Pessoal");
        listDataHeader.add("Hábitos Financeiros e Reflexões");

        List<String> principios_das_financas = new ArrayList<>();
        principios_das_financas.add("Compreenda melhor as finanças");
        principios_das_financas.add("Ganhe mais dinheiro");
        principios_das_financas.add("Gerencie melhor o seu dinheiro");
        principios_das_financas.add("Evite problemas financeiros");
        principios_das_financas.add("Erros que o tornam mais pobre");
        principios_das_financas.add("Meça o estado das suas finanças");
        principios_das_financas.add("Cartão de débito vs. cartão de crédito");

        List<String> poupanca = new ArrayList<>();
        poupanca.add("Truques que fazem você gastar mais");
        poupanca.add("Coisas das quais nos arrependemos de comprar");
        poupanca.add("Aprenda a reduzir despesas");
        poupanca.add("Por que devemos economizar");
        poupanca.add("Como economizar I");
        poupanca.add("Como economizar II");
        poupanca.add("Erros ao economizar");
        poupanca.add("Econômico vs. Mesquinho");

        List<String> livre_se_das_dividas = new ArrayList<>();
        livre_se_das_dividas.add("Como pagar minhas dívidas");
        livre_se_das_dividas.add("O custo de se endividar");
        livre_se_das_dividas.add("Decidir se devo pagar minhas dívidas");


        List<String> negocios_e_investimentos = new ArrayList<>();
        negocios_e_investimentos.add("Princípios básicos de empreendimento");
        negocios_e_investimentos.add("Limitações para o bom empreendedorismo");
        negocios_e_investimentos.add("Trabalhos Freelance");
        negocios_e_investimentos.add("Trabalho árduo vs. Trabalho inteligente");
        negocios_e_investimentos.add("Investir");

        List<String> desenvolvimento_pessoal = new ArrayList<>();
        desenvolvimento_pessoal.add("Comece a se desenvolver");
        desenvolvimento_pessoal.add("Elimine o que te limita");
        desenvolvimento_pessoal.add("Bons hábitos");
        desenvolvimento_pessoal.add("Seja um líder");
        desenvolvimento_pessoal.add("Vença a preguiça");


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
