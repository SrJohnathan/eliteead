package ead.elite.app.br.com.appelite.ead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import ead.elite.app.br.com.appelite.ead.adapter.AdapterProvas;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.dominio.Acerto;
import ead.elite.app.br.com.appelite.ead.dominio.Buscaprova;
import ead.elite.app.br.com.appelite.ead.dominio.PosicaoRes;
import ead.elite.app.br.com.appelite.ead.dominio.ProgressText;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickItemProva;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;


public class Prova extends AppCompatActivity implements OnClickItemProva {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button sim, nao;
    private Acerto acerto;
    private Buscaprova buscaprova;
    private boolean b = true;
    private FloatingActionButton floatingActionButton;
    private MaterialDialog materialDialog1;
    private PosicaoRes res1, res2, res3, res4, res5, res6, res7, res8, res9, res10;
    private int iduser;
    private boolean estado,clickfbtn = false,prova = false;
    private RecyclerView recyclerView;
    private AdapterProvas adapterProvas;
    private ArrayList<PosicaoRes> posicaoRes, savidintance;
    private ArrayList<ead.elite.app.br.com.appelite.ead.dominio.Prova> provas;
    private TextView contador;
    private Toolbar toolbar;
    private String[] quest;
    private int nota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prova);
        getPheferencias();
        buscaprova = getIntent().getParcelableExtra("prova");


        //IDS
        contador = (TextView) findViewById(R.id.contador);
        toolbar = (Toolbar) findViewById(R.id.toobarPai);
        toolbar.setTitle(buscaprova.getProva());


        ImageView cronome = (ImageView) findViewById(R.id.cronome);
        cronome.setImageDrawable(new IconicsDrawable(this).sizeDp(24).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_alarm));





        startService(new Intent("SERVICO_CO"));
        EventBus.getDefault().register(Prova.this);


        provas = new ArrayList<>();
        posicaoRes = new ArrayList<PosicaoRes>();
        recyclerView = (RecyclerView) findViewById(R.id.recyprova);


       final MaterialDialog  progressDialog = new MaterialDialog.Builder(this)
                .title("Carregando")
                .cancelable(false)
                .content("Carregando Conteudo")
                .progress(true, 0).build();
        progressDialog.show();


        if (savedInstanceState != null) {


            provas = savedInstanceState.getParcelableArrayList("prova");
            progressDialog.dismiss();
            adapterProvas = new AdapterProvas(Prova.this, provas);
            adapterProvas.setItemProva(Prova.this);
            recyclerView.setAdapter(adapterProvas);


            //RES

            res1 = savedInstanceState.getParcelable("res1");
            res2 = savedInstanceState.getParcelable("res2");
            res3 = savedInstanceState.getParcelable("res3");
            res4 = savedInstanceState.getParcelable("res4");
            res5 = savedInstanceState.getParcelable("res5");
            res6 = savedInstanceState.getParcelable("res6");
            res7 = savedInstanceState.getParcelable("res7");
            res8 = savedInstanceState.getParcelable("res8");
            res9 = savedInstanceState.getParcelable("res9");
            res10 = savedInstanceState.getParcelable("res10");

            prova = savedInstanceState.getBoolean("chave");


        } else {

            final HashMap<String, String> map = new HashMap<>();
            map.put("idcurso", buscaprova.getIdcurso() + "");
            map.put("prova", buscaprova.getProva());
            map.put("metodo", "getprova");

            Conexao.Conexao(this, Config.DOMIONIO + "/php/request/provarequest.php", map, new DadosVolley() {
                @Override
                public void geJsonObject(JSONObject jsonObject) {
                    Log.i("LOG", jsonObject + "");

                    if (jsonObject.has("0")) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            try {
                                JSONObject object = jsonObject.getJSONObject(String.valueOf(i));
                                int id = object.getInt("idquestao");
                                String pergunta = object.getString("pergunta");
                                String questao = object.getString("questao");
                                String rp1 = object.getString("rp1");
                                String rp2 = object.getString("rp2");
                                String rp3 = object.getString("rp3");
                                String rp4 = object.getString("rp4");
                                int idcurso = object.getInt("cursos_idcursos");
                                // String valor = object.getString()

                                ead.elite.app.br.com.appelite.ead.dominio.Prova prova = new ead.elite.app.br.com.appelite.ead.dominio.Prova(id, questao, pergunta, rp1, rp2, rp3, rp4, "certa", idcurso, iduser, 0);
                                provas.add(prova);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                        progressDialog.dismiss();
                        adapterProvas = new AdapterProvas(Prova.this, provas);
                        adapterProvas.setItemProva(Prova.this);
                        recyclerView.setAdapter(adapterProvas);

                    } else {
                        progressDialog.dismiss();

                        finish();

                    }
                }

                @Override
                public void ErrorVolley(String messege) {
                    progressDialog.dismiss();
                    finish();
                }
            });

        }


        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).sizeDp(30).icon(CommunityMaterial.Icon.cmd_check_all));


        recyclerView.setItemViewCacheSize(11);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    if (b == true) {
                        YoYo.with(Techniques.FadeOutUp).duration(500).playOn(floatingActionButton);
                        b = false;
                    }
                } else {
                    if (b == false) {
                        YoYo.with(Techniques.FadeInDown).duration(500).playOn(floatingActionButton);
                        b = true;
                    }
                }
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posicaoRes.clear();
                posicaoRes.add(res1);
                posicaoRes.add(res2);
                posicaoRes.add(res3);
                posicaoRes.add(res4);
                posicaoRes.add(res5);
                posicaoRes.add(res6);
                posicaoRes.add(res7);
                posicaoRes.add(res8);
                posicaoRes.add(res9);
                posicaoRes.add(res10);


                MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(Prova.this)
                        .negativeColor(Color.RED)
                        .positiveColor(Color.BLUE)
                        .title("Confirmação")
                        .content("Deseja terminar agora?")
                        .positiveText("Sim")
                        .negativeText("Não")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                progressDialog.show();
                                encerrar();
                                progressDialog.dismiss();
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });


                MaterialDialog dialog = materialDialog.build();
                dialog.show();

            }
        });

        LinearLayoutManager lln = new LinearLayoutManager(this);
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lln);


    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("prova", provas);

        //RES

        outState.putParcelable("res1", res1);
        outState.putParcelable("res1", res2);
        outState.putParcelable("res1", res3);
        outState.putParcelable("res1", res4);
        outState.putParcelable("res1", res5);
        outState.putParcelable("res1", res6);
        outState.putParcelable("res1", res7);
        outState.putParcelable("res1", res8);
        outState.putParcelable("res1", res9);
        outState.putParcelable("res1", res10);

        outState.putBoolean("chave",prova);
    }

    @Override
    public void onBackPressed() {

        if(clickfbtn){

            super.onBackPressed();

        }else {

            MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this).title("Sair da Prova")
                    .cancelable(false)
                    .content("Verifique se todas perguntas foram respondidas antes de sair. ").positiveText("SIM").negativeText("NAO")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            materialDialog1 = new MaterialDialog.Builder(Prova.this)
                                    .title("Carregando")
                                    .cancelable(false)
                                    .content("Carregando Conteudo")
                                    .showListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialogInterface) {

                                            encerrarBack();
                                        }
                                    }).dismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            Prova.super.onBackPressed();
                                        }
                                    }).progress(true, 0).build();
                            materialDialog1.show();

                        }
                    });
            materialDialog.show();


        }



    }



    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(prova){
            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(Prova.this);

    }

    public void getPheferencias() {
        SharedPreferences preferences = getSharedPreferences(Dados.LOGIN_NAME, Context.MODE_PRIVATE);
        iduser = preferences.getInt("2454", iduser);
        estado = preferences.getBoolean("12", estado);
    }

    @Override
    public void onClick(View view, int id, int response, int position) {
        provas.get(position).setAcerto(response);

        if (position == 0) {
            res1 = new PosicaoRes(id, response, position);
        } else if (position == 1) {
            res2 = new PosicaoRes(id, response, position);
        } else if (position == 2) {
            res3 = new PosicaoRes(id, response, position);
        } else if (position == 3) {
            res4 = new PosicaoRes(id, response, position);
        } else if (position == 4) {
            res5 = new PosicaoRes(id, response, position);
        } else if (position == 5) {
            res6 = new PosicaoRes(id, response, position);
        } else if (position == 6) {
            res7 = new PosicaoRes(id, response, position);
        } else if (position == 7) {
            res8 = new PosicaoRes(id, response, position);
        } else if (position == 8) {
            res9 = new PosicaoRes(id, response, position);
        } else if (position == 9) {
            res10 = new PosicaoRes(id, response, position);
        } else {
        }


    }

    private void encerrar() {
        acerto = new Acerto();
        Gson gson = new Gson();
        final String object = gson.toJson(posicaoRes);
        Log.i("LOG", object + "");
        HashMap<String, String> map1 = new HashMap();
        map1.put("metodo", "correcao");
        map1.put("resposta", object);
        map1.put("iduser", iduser + "");
        map1.put("idcurso", buscaprova.getIdcurso() + "");
        map1.put("prova", buscaprova.getProva());
        stopService(new Intent("SERVICO_CO"));
        Conexao.Conexao(Prova.this, Config.DOMIONIO + "/php/request/correcao.php", map1, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                    prova = true;
                try {
                    nota = jsonObject.getInt("acerto");
                    String acet = jsonObject.getString("certa");
                    quest = acet.split(Pattern.quote(","));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                acerto.setPontos(nota);
                ArrayList<Acerto.Estado> estados = new ArrayList<Acerto.Estado>();
                for (int i = 0; i < quest.length; i++) {

                    int o = Integer.parseInt(quest[i]);
                    Acerto.Estado estado = new Acerto.Estado(o, true);
                    estados.add(estado);

                }

                acerto.setQuestao(estados);
                adapterProvas = (AdapterProvas) recyclerView.getAdapter();
                adapterProvas.setAcerto(acerto, posicaoRes);
                clickfbtn = true;
            }

            @Override
            public void ErrorVolley(String messege) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProgressText progressText) {

        if (progressText.getProgrs().equals("acabou")) {

            encerrar();

        } else {
            contador.setText(progressText.getProgrs());
        }


    }

    private void encerrarBack() {

        posicaoRes.clear();
        posicaoRes.add(res1);
        posicaoRes.add(res2);
        posicaoRes.add(res3);
        posicaoRes.add(res4);
        posicaoRes.add(res5);
        posicaoRes.add(res6);
        posicaoRes.add(res7);
        posicaoRes.add(res8);
        posicaoRes.add(res9);
        posicaoRes.add(res10);


        acerto = new Acerto();
        Gson gson = new Gson();
        final String object = gson.toJson(posicaoRes);
        Log.i("LOG", object + "");
        HashMap<String, String> map1 = new HashMap();
        map1.put("metodo", "correcao");
        map1.put("resposta", object);
        map1.put("iduser", iduser + "");
        map1.put("idcurso", buscaprova.getIdcurso() + "");
        map1.put("prova", buscaprova.getProva());
        stopService(new Intent("SERVICO_CO"));
        Conexao.Conexao(Prova.this, Config.DOMIONIO + "/php/request/correcao.php", map1, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                materialDialog1.dismiss();


            }

            @Override
            public void ErrorVolley(String messege) {

            }
        });
    }
}
