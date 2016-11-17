package ead.elite.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.componets.CustonTextView;
import ead.elite.app.br.com.appelite.ead.dominio.Curso;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;
import ead.elite.app.br.com.appelite.ead.util.IabHelper;
import ead.elite.app.br.com.appelite.ead.util.IabResult;
import ead.elite.app.br.com.appelite.ead.util.Purchase;

/**
 * Created by Pc on 04/05/2016.
 */
public class CompraPag extends Fragment {
    private RelativeLayout btnCallPayment;
    private int iduser;
    private boolean estado = false;
    String numer, emailll;
    private List<String> skss;
    private IabHelper iabHelper;
    private TextView descricao, sumario, categoria, publico, preco, instrutor,aviso;
    private CustonTextView horas, titulo;
    private ImageView  instru, didn;
    private String tele;
    private CardView cardView;
    private double densidade;
    private int largura;
    private  int altura,pixell;
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
              rootView = inflater.inflate(R.layout.compra, container, false);
        }catch (Exception e){
            e.printStackTrace();
             rootView = inflater.inflate(R.layout.compra, container, false);

        }


        final Curso strtext = getActivity().getIntent().getParcelableExtra("put");


        //IDS
        descricao = (TextView) rootView.findViewById(R.id.descricao);
        sumario = (TextView) rootView.findViewById(R.id.sumario);
        aviso = (TextView) rootView.findViewById(R.id.aviso);
        instru = (ImageView) rootView.findViewById(R.id.imgins);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imgg);
        SimpleDraweeView  imgfun = (SimpleDraweeView) rootView.findViewById(R.id.fundimf);
        cardView = (CardView) rootView.findViewById(R.id.car4);


        didn = (ImageView) rootView.findViewById(R.id.didin);
        titulo = (CustonTextView) rootView.findViewById(R.id.titulo);
        instrutor = (TextView) rootView.findViewById(R.id.intrutor);
        horas = (CustonTextView) rootView.findViewById(R.id.temp);
        categoria = (TextView) rootView.findViewById(R.id.cate);
        publico = (TextView) rootView.findViewById(R.id.publico);
        preco = (TextView) rootView.findViewById(R.id.preco);


        //METODO
        descricao.setText(strtext.getDescrisao());
        sumario.setText(strtext.getSumario());
        titulo.setText(strtext.getNome().toUpperCase());
        instrutor.setText(strtext.getIntrutor());
        horas.setText(strtext.getHoras() + "Hrs");
        categoria.setText(strtext.getCategoria());

        publico.setText(strtext.getPublico());

        //  RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.estrela);

        //  LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        // layerDrawable.getDrawable(0).setColorFilter(getActivity().getResources().getColor(R.color.md_blue_200), PorterDuff.Mode.SRC_ATOP);
        //  layerDrawable.getDrawable(1).setColorFilter(getActivity().getResources().getColor(R.color.md_blue_200), PorterDuff.Mode.SRC_ATOP);
        //  layerDrawable.getDrawable(2).setColorFilter(getActivity().getResources().getColor(R.color.md_blue_600), PorterDuff.Mode.SRC_ATOP);

        densidade = getResources().getDisplayMetrics().density;
        largura = getResources().getDisplayMetrics().widthPixels - (int) (14 * densidade + 0.5f);
        altura = (largura / 16) * 9;
        pixell = (int) (6 * densidade + 0.5f);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Config.DOMIONIO + "/php/server/image.php?metodo=cursos&id=" + strtext.getId())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();

        imgfun.setController(controller);


        RoundingParams params2 = RoundingParams.fromCornersRadii(pixell,pixell,pixell,pixell);
        imgfun.getHierarchy().setRoundingParams(params2);

        Picasso.with(getActivity()).load(Config.DOMIONIO + "/php/server/image.php?metodo=cursos&id=" + strtext.getId()).into(imageView);
        Picasso.with(getActivity()).load(Config.DOMIONIO + "/php/server/image.php?metodo=instrutor&id=" + strtext.getFotoinstrutor()).into(instru);


        //  Picasso.with(getContext()).load("https://graph.facebook.com/1068399463199034/picture?height=100&width=100&migration_overrides=%7Boctober_2012%3Atrue%7D").into(imageView);
        btnCallPayment = (RelativeLayout) rootView.findViewById(R.id.comprar);


        getPheferencias();

        if (strtext.isPago() == true) {


            preco.setText(strtext.getPreco());
            aviso.setText("Certificação Gratuita");
            btnCallPayment.setVisibility(View.GONE);
            HashMap<String, String> map = new HashMap<>();
            map.put("metodo", "verificar");
            map.put("idalu", iduser + "");
            map.put("idcurso", strtext.getId() + "");
            Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/compra_cursos.php", map, new DadosVolley() {
                @Override
                public void geJsonObject(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getBoolean("resp")) {

                            btnCallPayment.setVisibility(View.VISIBLE);

                            didn.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_cash_usd));
                            btnCallPayment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (estado == true) {


                                        iabHelper = new IabHelper(getActivity(), Config.base64EncodedPublicKey);
                                        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                                            @Override
                                            public void onIabSetupFinished(IabResult result) {

                                                if (result.isFailure()) {

                                                    return;
                                                } else {

                                                    iabHelper.launchPurchaseFlow(getActivity(), strtext.getSku(), 217, new IabHelper.OnIabPurchaseFinishedListener() {
                                                        @Override
                                                        public void onIabPurchaseFinished(IabResult result, Purchase info) {

                                                            if (result.isFailure()) {

                                                                return;

                                                            } else {

                                                                if (info.getSku().equals(strtext.getSku())) {

                                                                    getPheferencias();
                                                                    HashMap<String, String> map = new HashMap<String, String>();
                                                                    map.put("metodo", "compra");
                                                                    map.put("valor", "gratis");
                                                                    map.put("idalu", iduser + "");
                                                                    map.put("idcurso", strtext.getId() + "");
                                                                    Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/compra_cursos.php", map, new DadosVolley() {
                                                                        @Override
                                                                        public void geJsonObject(JSONObject jsonObject) {
                                                                            Log.i("LOG", jsonObject + "");

                                                                            try {
                                                                                if (jsonObject.getBoolean("resp") == true) {

                                                                                    Snackbar.make(rootView, "Curso Assinado, Bem Vindo!!", Snackbar.LENGTH_LONG).show();

                                                                                } else if (jsonObject.getBoolean("resp") == false) {

                                                                                    Snackbar.make(rootView, "Voçê já é Assinante desse Curso", Snackbar.LENGTH_LONG).show();
                                                                                }
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }

                                                                        }

                                                                        @Override
                                                                        public void ErrorVolley(String messege) {

                                                                        }
                                                                    });


                                                                }

                                                            }
                                                        }
                                                    }, "");

                                                }

                                            }

                                        });


                                    } else {
                                        Snackbar.make(btnCallPayment, "Login Necessário", Snackbar.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        } else {

                            didn.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_check));
                            btnCallPayment.setVisibility(View.VISIBLE);
                            btnCallPayment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(rootView, "Este curso já está adquirido", Snackbar.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void ErrorVolley(String messege) {

                }
            });


        } else if (strtext.isPago() == false) {
            preco.setText("GRATUITO");
            aviso.setText("R$"+strtext.getPreco()+" Cetificação");
            didn.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_cash_usd));
            btnCallPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (estado == true) {
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                                .title("Assinar Cursos Gratis de " + strtext.getNome())
                                .content("Deseja Assinar Este Curso")
                                .negativeText("NAO")
                                .positiveText("SIM")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                                        getPheferencias();
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        map.put("metodo", "compra");
                                        map.put("valor", "gratis");
                                        map.put("idalu", iduser + "");
                                        map.put("idcurso", strtext.getId() + "");
                                        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/compra_cursos.php", map, new DadosVolley() {
                                            @Override
                                            public void geJsonObject(JSONObject jsonObject) {
                                                Log.i("LOG", jsonObject + "");

                                                try {
                                                    if (jsonObject.getBoolean("resp") == true) {
                                                        dialog.dismiss();
                                                        Snackbar.make(rootView, "Curso Assinado, Bem Vindo!!", Snackbar.LENGTH_LONG).show();

                                                    } else if (jsonObject.getBoolean("resp") == false) {
                                                        dialog.dismiss();
                                                        Snackbar.make(rootView, "Voçê já é Assinante desse Curso", Snackbar.LENGTH_LONG).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void ErrorVolley(String messege) {

                                            }
                                        });
                                    }
                                });

                        final MaterialDialog dialog = builder.build();
                        dialog.show();
                    } else {
                        Snackbar.make(btnCallPayment, "Login Necessário", Snackbar.LENGTH_SHORT).show();
                    }


                }
            });
        }


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (iabHelper != null) {
            iabHelper.dispose();
        }
        iabHelper = null;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void getPheferencias() {

        SharedPreferences preferences = getActivity().getSharedPreferences(Dados.LOGIN_NAME, Context.MODE_PRIVATE);
        iduser = preferences.getInt("2454", iduser);
        estado = preferences.getBoolean("12", estado);
        emailll = preferences.getString("245", "");
        tele = preferences.getString("1485", "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 217 && resultCode == getActivity().RESULT_OK) {
            if (iabHelper != null && iabHelper.handleActivityResult(requestCode, resultCode, data)) {
                super.onActivityResult(requestCode, resultCode, data);


            }
        }
    }

}
