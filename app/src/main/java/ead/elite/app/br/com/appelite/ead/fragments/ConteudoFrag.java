package ead.elite.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ead.elite.app.br.com.appelite.ead.Coneudo;
import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.adapter.AdapterConteudo;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.componets.DividerItemDecoration;
import ead.elite.app.br.com.appelite.ead.dominio.Capitulos;
import ead.elite.app.br.com.appelite.ead.dominio.Conteudo;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Pc on 10/11/2016.
 */

public class ConteudoFrag extends Fragment implements AdapterConteudo.OpemDrawer {

    private WebView webView;
    private View view;
    private Capitulos capitulos;
    private RecyclerView recyclerView;
    private int iduser;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FrameLayout drawer;
    private int position;
    private FloatingActionButton menufab, menufab2;
    private AdapterConteudo adapterConteudo;
    private ArrayList<Capitulos> capitulosArrayList;
    private NestedScrollView scrollView;
    private Conteudo conteudo;
    private MaterialDialog dialog;
    private Snackbar snackbar;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_conteudo, container, false);


        //IDS
        webView = (WebView) view.findViewById(R.id.conteudo);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.dl);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy);
        drawer = (FrameLayout) view.findViewById(R.id.framel);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toobar);
        menufab = (FloatingActionButton) getActivity().findViewById(R.id.menufab);
        menufab2 = (FloatingActionButton) view.findViewById(R.id.menufab);
        scrollView = (NestedScrollView) view.findViewById(R.id.scroll);


        setHasOptionsMenu(true);

        getActivity().setTitle("Conteúdos");


        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));


        conteudo = getActivity().getIntent().getParcelableExtra("conteudo");

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);


        recyclerView.setLayoutManager(llm);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(false);


        buscarMarcacao();


        return view;
    }

    public void preencher(final Capitulos capitulos) {
        String webContent = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\"></head>"
                + "<body> <h2 style=\" margin-left:30px;\">" + capitulos.getNome() + "</h2> <br> <br> " + capitulos.getTexto1() + " </h2> <br> <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script> <script   src=\"https://code.jquery.com/jquery-2.2.3.js\"   integrity=\"sha256-laXWtGydpwqJ8JA+X9x2miwmaiKhn8tVmOVEigRNtP4=\"   crossorigin=\"anonymous\"></script>" +
                "<script>$(document).ready(function(){\n" +
                "            $(\"img\").addClass(\"img-responsive\");\n" +
                "    });\n</script></body></html>";
        scrollView.scrollTo(0, 0);
        webView.loadData(webContent, "text/html", "UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                drawerLayout.openDrawer(drawer);

                if (capitulos.isStatus()) {
                    menufab.setBackgroundTintList(getResources().getColorStateList(R.color.md_green_500));
                    menufab.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_eye));


                } else {
                    menufab.setBackgroundTintList(getResources().getColorStateList(R.color.md_red_500));
                    menufab.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_eye_off));


                }
                dialog.dismiss();
                menufab.show();

            }
        });


    }

    public void getPheferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Dados.LOGIN_NAME, MODE_PRIVATE);
        iduser = preferences.getInt("2454", iduser);

    }


    public void buscarConteudo(int position) {
        Conteudo conteudo = getActivity().getIntent().getParcelableExtra("conteudo");
        webView.loadData("", "text/html", "UTF-8");
        getPheferencias();
        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "procura");
        map.put("idc", capitulosArrayList.get(position).getCap());
        map.put("idcu", conteudo.getId() + "");
        map.put("idalu", iduser + "");
        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/capitulosapp.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {

                boolean status;
                if (jsonObject.has("0")) {
                    try {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));

                            int id = object.getInt("idcapitulos");
                            String nome = object.getString("titulo");
                            String subti = object.getString("subti");
                            String cap = object.getString("capiti_nume");
                            if (object.has("leu")) {

                                status = object.getBoolean("leu");
                            } else {
                                status = false;
                            }

                            String idcur = object.getString("cursos_idcursos");
                            String texto = object.getString("texto");


                            capitulos = new Capitulos(id, nome, status, cap, subti, texto, null);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                toolbar.setTitle(capitulos.getCap());
                toolbar.setSubtitle(capitulos.getNome());
                preencher(capitulos);


            }

            @Override
            public void ErrorVolley(String messege) {
                Log.i("LOG", messege);
                dialog.dismiss();
                Snackbar.make(view,"Erro na conexão com a internet",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public void buscarMarcacao() {

        Conteudo conteudo = getActivity().getIntent().getParcelableExtra("conteudo");
        getPheferencias();
        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "pontomarcar");
        map.put("idcu", conteudo.getId() + "");
        map.put("idalu", iduser + "");
        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/capitulosapp.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                capitulosArrayList = new ArrayList<Capitulos>();
                boolean status;
                if (jsonObject.has("0")) {
                    try {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));

                            int id = object.getInt("idcapitulos");
                            String titulo = object.getString("titulo");
                            String cap = object.getString("capiti_nume");
                            if (object.has("leu")) {

                                status = object.getBoolean("leu");
                            } else {
                                status = false;
                            }


                            Capitulos capitulo = new Capitulos(id, titulo, status, cap, null, null, null);
                            capitulosArrayList.add(capitulo);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                adapterConteudo = new AdapterConteudo(getActivity(), capitulosArrayList);
                recyclerView.setAdapter(adapterConteudo);
                adapterConteudo.setOpemDrawer(ConteudoFrag.this);


            }

            @Override
            public void ErrorVolley(String messege) {
                Log.i("LOG", messege);
              snackbar =  Snackbar.make(view, "Verifique sua Conexão", Snackbar.LENGTH_INDEFINITE).setAction("CONFIG", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        getActivity().startActivity(intent);
                        snackbar.dismiss();
                    }
                });

            }
        });


    }

    @Override
    public void Click(View v, int position) {

        this.position = position;
        buscarConteudo(position);
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        menufab.hide();


        dialog = new MaterialDialog.Builder(getActivity())
                .title("Carregando")
                .cancelable(false)
                .content("Carregando Conteudo")
                .progress(true, 0).build();




        ((Coneudo)getActivity()).setDrawerLayout(drawerLayout,drawer);


        menufab2.setBackgroundTintList(getResources().getColorStateList(R.color.md_green_500));
        menufab2.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_video));
        menufab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "VIDEO", Toast.LENGTH_SHORT).show();
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > 10) {
                    menufab2.setBackgroundTintList(getResources().getColorStateList(R.color.md_purple_500));
                    menufab2.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_arrow_up));
                    menufab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            scrollView.scrollTo(0, 0);
                        }
                    });
                } else {
                    menufab2.setBackgroundTintList(getResources().getColorStateList(R.color.md_green_500));
                    menufab2.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_video));
                    menufab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Toast.makeText(getActivity(), "VIDEO", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                menufab.hide();
                ((Coneudo)getActivity()).naomostrarbt();
                getActivity().setTitle("Conteúdos");


            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                menufab.show();
                ((Coneudo)getActivity()).mostrarbt();
                toolbar.setSubtitle("");


            }
        });


        menufab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (capitulos.getNome().length() > 0) {
                    if (capitulos.isStatus()) {
                        Snackbar.make(view, "Capitulo já Analizado ", Snackbar.LENGTH_SHORT).show();

                    } else {
                        capitulos.setStatus(true);
                        macar();

                    }
                } else {
                    Snackbar.make(view, "Nenhum Capitulo Selecionado", Snackbar.LENGTH_SHORT).show();
                }


            }


        });

    }

    public void macar() {
        HashMap<String, String> map = new HashMap<>();
        map.put("idalu", iduser + "");
        map.put("idcu", conteudo.getId() + "");
        map.put("metodo", "marcar");
        map.put("leu", capitulos.isStatus() + "");
        map.put("idc", capitulos.getId() + "");

        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/capitulosapp.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {

                try {
                    if (jsonObject.getBoolean("status")) {
                        menufab.hide();
                        menufab.setBackgroundTintList(getResources().getColorStateList(R.color.md_green_500));
                        menufab.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_eye));
                        menufab.show();

                    }
                    capitulosArrayList.get(position).setStatus(jsonObject.getBoolean("status"));
                    adapterConteudo.atualizarList(capitulosArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void ErrorVolley(String messege) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            drawerLayout.closeDrawer(drawer);
        }

        return true;
    }
}
