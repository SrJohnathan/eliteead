package ead.elite.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.Drawer;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import br.com.uol.ps.library.PagSeguro;
import br.com.uol.ps.library.PagSeguroRequest;
import br.com.uol.ps.library.PagSeguroResponse;
import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.dominio.Curso;
import ead.elite.app.br.com.appelite.ead.extras.ValidaCPF;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;

/**
 * Created by Pc on 04/05/2016.
 */
public class CompraPag extends Fragment {
    private ImageButton btnCallPayment;
    private int iduser;
    private boolean estado = false;
    String numer, emailll;
    private TextView descricao, sumario, titulo, horas, categoria, publico, preco, instrutor;
    private ImageView imageView, instru;
    private Curso curso;
    private String cpf;
    private String tele;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.compra, container, false);

        final Curso strtext = getActivity().getIntent().getParcelableExtra("put");





        //IDS
        descricao = (TextView) rootView.findViewById(R.id.descricao);
        sumario = (TextView) rootView.findViewById(R.id.sumario);
        imageView = (ImageView) rootView.findViewById(R.id.img);
        instru = (ImageView) rootView.findViewById(R.id.imgins);
        titulo = (TextView) rootView.findViewById(R.id.titulo);
        instrutor = (TextView) rootView.findViewById(R.id.intrutor);
        horas = (TextView) rootView.findViewById(R.id.tempo);
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

        Picasso.with(getActivity()).load(Config.DOMIONIO+"/php/server/image.php?metodo=cursos&id=" + strtext.getId()).into(imageView);
        Picasso.with(getActivity()).load(Config.DOMIONIO+"/php/server/image.php?metodo=instrutor&id=" + strtext.getFotoinstrutor()).into(instru);


        //  Picasso.with(getContext()).load("https://graph.facebook.com/1068399463199034/picture?height=100&width=100&migration_overrides=%7Boctober_2012%3Atrue%7D").into(imageView);
        btnCallPayment = (ImageButton) rootView.findViewById(R.id.comprar);


        getPheferencias();

        if (strtext.isPago() == true) {
            preco.setText("R$" + strtext.getPreco());
            btnCallPayment.setVisibility(View.GONE);
            HashMap<String,String> map = new HashMap<>();
            map.put("metodo","verificar");
            map.put("idalu", iduser + "");
            map.put("idcurso", strtext.getId() + "");
            Conexao.Conexao(getActivity(), Config.DOMIONIO+"/php/request/compra_cursos.php", map, new DadosVolley() {
                @Override
                public void geJsonObject(JSONObject jsonObject) {
                    try {
                        if(jsonObject.getBoolean("resp")){

                            btnCallPayment.setVisibility(View.VISIBLE);

                            btnCallPayment.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(FontAwesome.Icon.faw_shopping_cart));
                            btnCallPayment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (estado == true) {

                                        final View view1 = View.inflate(getActivity(), R.layout.compra_decisao, null);
                                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                                                .titleColor(Color.BLUE)
                                                .customView(view1, true);
                                        final MaterialDialog dialog = builder.build();
                                        dialog.show();


                                        final ImageButton button = (ImageButton) view1.findViewById(R.id.confirma);
                                        final ImageButton nao = (ImageButton) view1.findViewById(R.id.nao);
                                        button.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(20).color(Color.WHITE).icon(FontAwesome.Icon.faw_shopping_cart));
                                        final TextView pecro = (TextView) view1.findViewById(R.id.valor);
                                        final TextView nome = (TextView) view1.findViewById(R.id.nome);
                                        nome.setText(strtext.getNome());
                                        nao.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(20).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_close_circle));

                                        nao.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                        //   TelephonyManager tMgr = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                                        //  String mPhoneNumber = tMgr.getLine1Number();

                                        pecro.setText("R$" + strtext.getPreco());

                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Toast.makeText(getActivity(),emailll,Toast.LENGTH_SHORT).show();

                                                BigDecimal amount = new BigDecimal(strtext.getPreco());
                                                //quantidade de parcelas
                                                Log.i("LOG", amount + "");
                                                dialog.dismiss();
                                                int quantityParcel = 1;
                                                getPheferencias();
                                                PagSeguro.pay(new PagSeguroRequest()
                                                                .withNewItem(strtext.getNome(), quantityParcel, amount)
                                                                .withVendorEmail("seu email cadastrado no pagseguro")
                                                                .withBuyerEmail(emailll)
                                                                 .withBuyerCPF(cpf)
                                                                    .withBuyerCellphoneNumber("55" + numer)
                                                                .withReferenceCode("123")
                                                                .withEnvironment(PagSeguro.Environment.PRODUCTION)
                                                                .withAuthorization("seu email de login no pagseguro", "codigo obtido no pagseguro.uol.com.br"),

                                                        getActivity(),
                                                        R.id.relativ,
                                                        new PagSeguro.PagSeguroListener() {
                                                            @Override
                                                            public void onSuccess(PagSeguroResponse response, Context context) {
                                                                Toast.makeText(context, "Lib PS retornou pagamento aprovado! " + response, Toast.LENGTH_LONG).show();
                                                            }

                                                            @Override
                                                            public void onFailure(PagSeguroResponse response, Context context) {
                                                                Toast.makeText(context, "Lib PS retornou FALHA no pagamento! " + response, Toast.LENGTH_LONG).show();
                                                            }
                                                        });


                                            }
                                        });


                                    } else {
                                        Snackbar.make(btnCallPayment, "Login Necessário", Snackbar.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        }else {

                            btnCallPayment.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_check));
                            btnCallPayment.setBackgroundResource(R.drawable.btazul);
                            btnCallPayment.setVisibility(View.VISIBLE);
                            btnCallPayment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(rootView,"Este curso já está adquirido",Snackbar.LENGTH_SHORT).show();
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
            preco.setText("Gratuito");
            preco.setTextColor(getActivity().getResources().getColor(R.color.md_green_600));
            btnCallPayment.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(FontAwesome.Icon.faw_thumbs_o_up));
            btnCallPayment.setBackgroundResource(R.drawable.btroxo);
            btnCallPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (estado == true) {
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                                .title("Assinar Cursos Gratis de "+ strtext.getNome())
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
                                        Conexao.Conexao(getActivity(), Config.DOMIONIO+"/php/request/compra_cursos.php", map, new DadosVolley() {
                                            @Override
                                            public void geJsonObject(JSONObject jsonObject) {
                                                Log.i("LOG",jsonObject+"");

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
    }






}
