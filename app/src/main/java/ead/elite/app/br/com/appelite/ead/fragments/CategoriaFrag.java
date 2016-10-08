package ead.elite.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ead.elite.app.br.com.appelite.ead.AtividadeContainer;
import ead.elite.app.br.com.appelite.ead.AtividadePai;
import ead.elite.app.br.com.appelite.ead.AtividadeTabs;
import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.adapter.AdapterInformatica;
import ead.elite.app.br.com.appelite.ead.dominio.Curso;
import ead.elite.app.br.com.appelite.ead.interfaces.ArrayDados;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickFrag;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;

/**
 * Created by Pc on 15/07/2016.
 */
public  class CategoriaFrag extends Fragment implements OnClickFrag {

    AdapterInformatica informatica;
    private ArrayList<Curso> curso;
    private View view;
    private String categoria;
    private Bundle savedInstanceState;
    private SearchView searchView;
    private ArrayList<Curso> mListAux;
    private RecyclerView view1;
    private ProgressBar progressBar;
    private TextView connection;


    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;

        categoria = getArguments().getString("tabs");

        view = inflater.inflate(R.layout.container3, container, false);


        //IDS
        view1 = (RecyclerView) view.findViewById(R.id.view);
        connection = (TextView) view.findViewById(R.id.connetion);
        progressBar = (ProgressBar) view.findViewById(R.id.progrss);


        connection.setCompoundDrawables(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.LTGRAY).icon(CommunityMaterial.Icon.cmd_alert_circle), null, null, null);
        setHasOptionsMenu(true);



        //INSTANCIAS
        curso = new ArrayList<>();


        //ADAPTER INFORMATICA


        //PARAMETROS
        view1.setHasFixedSize(true);


        //ORIENTAÇÃO
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        view1.setLayoutManager(llm);


        return (view);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cursos", curso);
    }

    public void getDados(final ArrayDados dados) {

        //NET
        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "categoria");
        map.put("cate",categoria);
        Conexao.Conexao(getActivity(), Config.DOMIONIO+"/php/request/dados.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {



                try {

                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject object = jsonObject.getJSONObject(String.valueOf(i));

                        int id = object.getInt("idcursos");
                        String nome = object.getString("nome");
                        String preco = object.getString("preco");
                        String sumario = object.getString("sumario");
                        boolean pago = object.getBoolean("cu_pago");
                        String horas = object.getString("horas");
                        String fotoinst = object.getString("instrutor_idinstrutor");
                        String instrutor = object.getString("nome_int");
                        String descricao = object.getString("descricao");
                        String publico = object.getString("publico");
                        String categoria = object.getString("nomecate");


                        Curso cursos = new Curso(id, nome, categoria, horas, instrutor, 1, fotoinst, descricao, sumario, publico, preco, 1, pago);
                        curso.add(cursos);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dados.getCursos(curso);

            }

            @Override
            public void ErrorVolley(String messege) {
                progressBar.setVisibility(View.GONE);
                connection.setVisibility(View.VISIBLE);
                Snackbar.make(view, "Verifique sua Conexão com a Internet", Snackbar.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void Click(View view, int position) {

        Intent intent = new Intent(getActivity(), AtividadeContainer.class);
        intent.putExtra("fra", 5);
        intent.putExtra("put",curso.get(position));
        startActivity(intent);


    }

    public void getSeache(String pea) {

        informatica.atualizar(filterCars(pea));
    }


    public List<Curso> filterCars(String q) {
        mListAux = new ArrayList<>();

        for (int i = 0, tamI = curso.size(); i < tamI; i++) {
            if (curso.get(i).getNome().equalsIgnoreCase(q)) {
                mListAux.add(curso.get(i));
            }
        }
        for (int i = 0, tamI = curso.size(); i < tamI; i++) {
            if (!mListAux.contains(curso.get(i))
                    && curso.get(i).getCategoria().equalsIgnoreCase(q)) {
                mListAux.add(curso.get(i));
            }
        }

        return mListAux;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.pesquisa));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Pesquisar");
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.BLACK);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.LTGRAY);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setBackgroundColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSeache(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (!curso.isEmpty()) {
                    informatica.atualizar(curso);
                }

                return false;
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        if (savedInstanceState != null) {
            ArrayList arrayList = (ArrayList) savedInstanceState.getParcelableArrayList("cursos");
            curso = arrayList;
            informatica = new AdapterInformatica(getActivity(), curso);
            progressBar.setVisibility(View.GONE);
            view1.setAdapter(informatica);
            informatica.setOnClickFrag(CategoriaFrag.this);


        } else if (curso == null || curso.size() == 0) {

            getDados(new ArrayDados() {
                @Override
                public void getCursos(ArrayList<Curso> cursos) {
                    curso = cursos;
                    if (cursos.size() == 0) {
                        progressBar.setVisibility(View.GONE);
                        connection.setVisibility(View.VISIBLE);

                    } else {
                        informatica = new AdapterInformatica(getActivity(), curso);
                        progressBar.setVisibility(View.GONE);
                        view1.setAdapter(informatica);
                        informatica.setOnClickFrag(CategoriaFrag.this);
                    }


                }


            });

        }
    }
}
