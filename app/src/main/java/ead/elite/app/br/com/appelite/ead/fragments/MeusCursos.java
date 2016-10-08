package ead.elite.app.br.com.appelite.ead.fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ead.elite.app.br.com.appelite.ead.AtividadePai;
import ead.elite.app.br.com.appelite.ead.Coneudo;
import ead.elite.app.br.com.appelite.ead.Prova;
import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.adapter.AdapterMeusCursos;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.bd.NetDados;
import ead.elite.app.br.com.appelite.ead.dominio.Buscaprova;
import ead.elite.app.br.com.appelite.ead.dominio.Conteudo;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickFrag;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;


public class MeusCursos extends Fragment implements OnClickFrag {
    RecyclerView recyclerView;
    AdapterMeusCursos adapterMeusCursos;
    private int iduser;
    private ArrayList<Conteudo> conteudo;
    private ProgressBar progressBar;
    private FloatingActionButton menufab;
    private boolean estado;
    private ImageButton imageButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.container, container, false);


        //IDS
        recyclerView = (RecyclerView) view.findViewById(R.id.view);
        menufab = (FloatingActionButton) view.findViewById(R.id.menufab);
        progressBar = (ProgressBar) view.findViewById(R.id.progrss);
        imageButton = (ImageButton) getActivity().findViewById(R.id.btexcluir);


        //  menufab.setIndeterminate(true);
        //  menufab.setShowProgressBackground(false);




        menufab.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_reload).sizeDp(24).color(Color.WHITE));
        menufab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MeusCursos frag = new MeusCursos();
                FragmentTransaction manager = getActivity().getFragmentManager().beginTransaction();
                manager.replace(R.id.relative, frag, "fraf");
                manager.commit();


            }
        });


        //PARAMETROS
        recyclerView.setHasFixedSize(true);


        //ORIENTAÇÃO
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView.setLayoutManager(llm);


        return view;
    }

    @Override
    public void Click(View view, final int position) {

        switch (view.getId()) {
            case R.id.btprova:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = View.inflate(getActivity(), R.layout.avisoprova, null);
                builder.setView(view1);
                final AlertDialog dialog = builder.create();


                final ImageView porta1 = (ImageView) view1.findViewById(R.id.porta1);
                final ImageView porta2 = (ImageView) view1.findViewById(R.id.porta2);

                final RelativeLayout prova1 = (RelativeLayout) view1.findViewById(R.id.btprova1);
                final RelativeLayout prova2 = (RelativeLayout) view1.findViewById(R.id.btprova2);


                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(porta1, "scaleX", 0.7f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(porta1, "scaleY", 0.7f);
                scaleDownY.setDuration(700);
                scaleDownY.setRepeatCount(10);
                scaleDownY.setRepeatMode(ObjectAnimator.REVERSE);
                scaleDownX.setRepeatMode(ObjectAnimator.REVERSE);
                scaleDownX.setDuration(700);
                scaleDownX.setRepeatCount(10);
                final AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);


                ObjectAnimator scaleDownX1 = ObjectAnimator.ofFloat(porta2, "scaleX", 0.7f);
                ObjectAnimator scaleDownY1 = ObjectAnimator.ofFloat(porta2, "scaleY", 0.7f);
                scaleDownY1.setDuration(700);
                scaleDownY1.setRepeatCount(10);
                scaleDownY1.setRepeatMode(ObjectAnimator.REVERSE);
                scaleDownX1.setRepeatMode(ObjectAnimator.REVERSE);
                scaleDownX1.setDuration(700);
                scaleDownX1.setRepeatCount(10);
                final AnimatorSet scaleDown1 = new AnimatorSet();
                scaleDown1.play(scaleDownX1).with(scaleDownY1);


                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Processando...");
                progressDialog.setIndeterminate(true);
                // progressDialog.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
                progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);


                HashMap<String, String> map = new HashMap<>();
                map.put("idcurso", conteudo.get(position).getId() + "");
                map.put("iduser", conteudo.get(position).getIdAluno() + "");
                map.put("prova", "prova");
                map.put("metodo", "get");

                Conexao.Conexao(getActivity(), Config.DOMIONIO+"/php/request/notas.php", map, new DadosVolley() {
                    @Override
                    public void geJsonObject(JSONObject jsonObject) {

                        if (jsonObject != null) {
                            Log.i("LOG", jsonObject + "");
                            dialog.show();
                            try {
                                if (jsonObject.getJSONObject("0").getString("nota1").equalsIgnoreCase("null") && conteudo.get(position).getPorce() >= 5) {
                                    scaleDown.start();
                                    porta1.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(50).icon(MaterialDesignIconic.Icon.gmi_check).color(Color.GREEN));
                                    prova1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            progressDialog.show();
                                            Intent intent = new Intent(getActivity(), Prova.class);
                                            Buscaprova buscaprova = new Buscaprova(iduser, conteudo.get(position).getId(), "Prova 1");
                                            intent.putExtra("prova", buscaprova);
                                            progressDialog.dismiss();
                                            getActivity().startActivity(intent);

                                        }
                                    });

                                } else {
                                    porta1.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(50).icon(MaterialDesignIconic.Icon.gmi_block).color(Color.LTGRAY));

                                }
                                if (conteudo.get(position).getPorce() < 5 && jsonObject.getJSONObject("0").getString("nota1").equalsIgnoreCase("null")) {

                                    scaleDown.start();
                                    porta1.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(50).icon(MaterialDesignIconic.Icon.gmi_block).color(Color.RED));

                                }

                                if (jsonObject.getJSONObject("0").getString("nota2").equalsIgnoreCase("null") && conteudo.get(position).getPorce() == 10) {
                                    scaleDown1.start();
                                    porta2.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(50).icon(MaterialDesignIconic.Icon.gmi_check).color(Color.GREEN));
                                    prova2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            progressDialog.show();
                                            Intent intent = new Intent(getActivity(), Prova.class);
                                            Buscaprova buscaprova = new Buscaprova(iduser, conteudo.get(position).getId(), "Prova 2");
                                            intent.putExtra("prova", buscaprova);
                                            progressDialog.dismiss();
                                            getActivity().startActivity(intent);

                                        }
                                    });

                                } else {

                                    porta2.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(50).icon(MaterialDesignIconic.Icon.gmi_block).color(Color.LTGRAY));
                                }

                                if (conteudo.get(position).getPorce() < 10 && jsonObject.getJSONObject("0").getString("nota2").equalsIgnoreCase("null")) {
                                    scaleDown1.start();
                                    porta2.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(50).icon(MaterialDesignIconic.Icon.gmi_block).color(Color.RED));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void ErrorVolley(String messege) {

                        progressDialog.dismiss();
                        Snackbar.make(menufab, "Verifique sua Conexão com a Internet", Snackbar.LENGTH_SHORT).show();
                    }
                });


                break;

            case R.id.conteudo:
                Intent intent = new Intent(getActivity(), Coneudo.class);
                intent.putExtra("conteudo", conteudo.get(position));
                startActivity(intent);
                break;

            case R.id.notas :
                View view2 = View.inflate(getActivity(),R.layout.notas,null);

                final TextView nota1 = (TextView) view2.findViewById(R.id.nota1);
                final TextView nota2 = (TextView) view2.findViewById(R.id.nota2);
                final TextView nota3 = (TextView) view2.findViewById(R.id.nota3);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity())
                        .setView(view2).setTitle("Notas do Curso de "+ conteudo.get(position).getNome());
                final AlertDialog dialog1 = builder1.create();

                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("idcurso", conteudo.get(position).getId() + "");
                mapa.put("iduser", conteudo.get(position).getIdAluno() + "");
                mapa.put("prova", "prova");
                mapa.put("metodo", "get");

                Conexao.Conexao(getActivity(),Config.DOMIONIO+"/php/request/notas.php", mapa, new DadosVolley() {
                    @Override
                    public void geJsonObject(JSONObject jsonObject) {

                        try {
                            if(jsonObject.getJSONObject("0").getString("nota1").equalsIgnoreCase("null")){

                                nota1.setText("SEM NOTA");
                                nota1.setTextColor(Color.LTGRAY);

                            }else {

                                nota1.setText(jsonObject.getJSONObject("0").getString("nota1"));
                                if(Integer.parseInt(jsonObject.getJSONObject("0").getString("nota1")) < 5){
                                    nota1.setTextColor(Color.RED);
                                }else if(Integer.parseInt(jsonObject.getJSONObject("0").getString("nota1")) == 5
                                        || Integer.parseInt(jsonObject.getJSONObject("0").getString("nota1")) == 6 ){
                                    nota1.setTextColor(Color.GREEN);
                                }else if(Integer.parseInt(jsonObject.getJSONObject("0").getString("nota1")) >= 7){
                                    nota1.setTextColor(Color.BLUE);
                                }


                            }

                            if(jsonObject.getJSONObject("0").getString("nota2").equalsIgnoreCase("null")){

                                nota2.setText("SEM NOTA");
                                nota2.setTextColor(Color.LTGRAY);

                            }else {

                                nota2.setText(jsonObject.getJSONObject("0").getString("nota2"));
                                if(Integer.parseInt(jsonObject.getJSONObject("0").getString("nota2")) < 5){
                                    nota2.setTextColor(Color.RED);
                                }else if(Integer.parseInt(jsonObject.getJSONObject("0").getString("nota2")) == 5
                                        || Integer.parseInt(jsonObject.getJSONObject("0").getString("nota2")) == 6 ){
                                    nota2.setTextColor(Color.GREEN);
                                }else if(Integer.parseInt(jsonObject.getJSONObject("0").getString("nota2")) >= 7){
                                    nota2.setTextColor(Color.BLUE);
                                }


                            }

                            if(jsonObject.getJSONObject("0").getString("nota2").equalsIgnoreCase("null") || jsonObject.getJSONObject("0").getString("nota1").equalsIgnoreCase("null")){
                                nota3.setText("SEM NOTA");
                                nota3.setTextColor(Color.LTGRAY);
                            }else {

                                    double not = (Double.parseDouble(jsonObject.getJSONObject("0").getString("nota1")) + Double.parseDouble(jsonObject.getJSONObject("0").getString("nota2"))) / 2;
                                    nota3.setText(String.valueOf(not));
                                    if (not < 5) {
                                        nota3.setTextColor(Color.RED);
                                    } else if (not == 5
                                            || not == 6) {
                                        nota3.setTextColor(Color.GREEN);
                                    } else if (not >= 7) {
                                        nota3.setTextColor(Color.BLUE);
                                    }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog1.show();
                    }

                    @Override
                    public void ErrorVolley(String messege) {

                    }
                });


                break;
        }


    }

    @Override
    public void onStart() {
        super.onStart();



        getPheferencias();

        if (estado != true) {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
            startActivity(new Intent(getActivity(), AtividadePai.class));
            getActivity().finish();
        }

        getPheferencias();

        getlist(iduser, new GetConteudo() {
            @Override
            public void get(ArrayList<Conteudo> conteudos) {
                conteudo = conteudos;
                adapterMeusCursos = new AdapterMeusCursos(getActivity(), conteudos);
                adapterMeusCursos.setOnClickFrag(MeusCursos.this);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapterMeusCursos);
                NetDados dados = new NetDados(getActivity());
                dados.setDados("147", String.valueOf(conteudos.size()));
                dados.Commit();


            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    public void getPheferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Dados.LOGIN_NAME, Context.MODE_PRIVATE);
        iduser = preferences.getInt("2454", iduser);
        estado = preferences.getBoolean("12", estado);
    }

    public void getlist(int id, final GetConteudo dados) {

        if (estado == true) {

            HashMap<String, String> map = new HashMap<>();

            map.put("metodo", "getalucu");
            map.put("id", String.valueOf(id));
            final ArrayList<Conteudo> list = new ArrayList<>();

            Conexao.Conexao(getActivity(), Config.DOMIONIO+"/php/request/appconte.php", map, new DadosVolley() {
                @Override
                public void geJsonObject(JSONObject jsonObject) {
                    int porce;
                    for (int i = 0; i < jsonObject.length(); i++) {
                        try {
                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));
                            int id = object.getInt("idcursos");
                            String nome = object.getString("nome");
                            if (object.has("por_final_alu") == false) {
                                porce = 0;
                            } else {
                                if (object.getString("por_final_alu").equals("null")) {
                                    porce = 0;
                                } else {
                                    porce = object.getInt("por_final_alu");
                                }

                            }


                            Conteudo conteudo = new Conteudo(nome, id, porce, iduser);
                            list.add(conteudo);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    dados.get(list);
                }

                @Override
                public void ErrorVolley(String messege) {
                    progressBar.setVisibility(View.GONE);

                    Snackbar.make(menufab, "Verifique sua Conexão com a Internet", Snackbar.LENGTH_SHORT).show();

                }
            });

        }


    }

    interface GetConteudo {
        public void get(ArrayList<Conteudo> conteudos);
    }



}
