package ead.elite.app.br.com.appelite.ead.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.barteksc.pdfviewer.PDFView;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.uol.ps.library.PagSeguro;
import br.com.uol.ps.library.PagSeguroRequest;
import br.com.uol.ps.library.PagSeguroResponse;
import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.adapter.AdapterCertificado;
import ead.elite.app.br.com.appelite.ead.aplication.App;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.dominio.Certificados;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.download.GetDownloads;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;


/**
 * Created by Pc on 03/05/2016.
 */
public class Certificado extends Fragment implements AdapterCertificado.GetClick {
    private View view;
    private Snackbar snackbar;
    private RecyclerView recyclerView;
    private int iduser;
    private boolean estado;
    private ArrayList<Certificados> list;
    private AdapterCertificado certificado;
    public static final int REQUESTPERMISSION = 55;
    public static final int REQUESTPERMISSI = 45;
    private int position;
    private String emailll,tele;
    private ProgressBar progressBar;
    private TextView connection;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.containertwo, container, false);
        getPheferencias();

        recyclerView = (RecyclerView) view.findViewById(R.id.recy);
        progressBar = (ProgressBar) view.findViewById(R.id.progss);
        connection = (TextView) view.findViewById(R.id.error);
        progressBar.setVisibility(View.VISIBLE);



        // INAPPP














        connection.setCompoundDrawables(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.LTGRAY).icon(CommunityMaterial.Icon.cmd_alert_circle), null, null, null);
        connection.setText("  Verifique sua Conexão com a Internet");

        list = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();
        map.put("user", iduser + "");
        map.put("certi", "get");
        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/certificado.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {

                Log.i("LOG",jsonObject+"");

                for (int i = 0; i < jsonObject.length(); i++) {
                    try {
                        JSONObject object = jsonObject.getJSONObject("" + i);
                        if (object.has("idc")) {
                            int id = object.getInt("idc");
                            double nota = object.getDouble("nota");
                            String nome = object.getString("curso");
                            boolean pago = object.getBoolean("pago");
                            String datap = object.getString("datap");
                            String data = object.getString("dataf");
                            String hora = object.getString("hora");
                            boolean baixou = object.getBoolean("baixou");

                            Certificados certificados = new Certificados(id, nome, nota, hora,pago, datap, data,baixou);
                            list.add(certificados);
                        } else if (object.has("nada")) {
                            connection.setText("  " + object.getString("nada"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (FragmentCompat.shouldShowRequestPermissionRationale(Certificado.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {
                        FragmentCompat.requestPermissions(Certificado.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSI);
                    }
                } else {
                    certificado = new AdapterCertificado(getActivity(), list);
                    if (list.size() == 0) {
                        Log.i("LOG","cer"+list.size());
                        connection.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        Log.i("LOG","cer"+list.size());
                        recyclerView.setAdapter(certificado);
                        progressBar.setVisibility(View.GONE);
                        connection.setVisibility(View.GONE);
                        certificado.setClick(Certificado.this);
                    }


                }


            }

            @Override
            public void ErrorVolley(String messege) {
                connection.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view, "Verifique sua Conexão com a Internet", Snackbar.LENGTH_SHORT).show();

            }
        });


        recyclerView.setHasFixedSize(true);


        LinearLayoutManager lln = new LinearLayoutManager(getActivity());
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lln);


        return view;
    }

    public void getPheferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Dados.LOGIN_NAME, Context.MODE_PRIVATE);
        iduser = preferences.getInt("2454", iduser);
        estado = preferences.getBoolean("12", estado);
        emailll = preferences.getString("245", "");
        tele = preferences.getString("1485","");
    }

    @Override
    public void Click(View view, int positon) {

        position = positon;

        String youFilePath = Environment.getExternalStorageDirectory().toString() + "/EliteEad";
        File file = new File(youFilePath, list.get(positon).getNome() + ".pdf");
        if (file.exists() == true) {
            View view1 = View.inflate(getActivity(), R.layout.pdfview, null);
            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                    .customView(view1, false);
            MaterialDialog dialog = builder.build();
            PDFView pdfView = (PDFView) view1.findViewById(R.id.pdfViewPager);
            pdfView.fromFile(file).load();


            dialog.show();
        } else if (file.exists() == false) {

            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (FragmentCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    FragmentCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
                }
            } else {

                if (!list.get(positon).isBaixou()) {

                    compra();

                } else {

                    snackbar = Snackbar.make(view, "Este arquivo já foi Baixado", Snackbar.LENGTH_SHORT).setAction("OK!", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();

                        }
                    });

                    snackbar.show();

                }


            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUESTPERMISSION:
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        if (!list.get(position).isBaixou()) {

                            compra();

                        } else {

                            snackbar = Snackbar.make(view, "Este arquivo já foi Baixado", Snackbar.LENGTH_LONG).setAction("OK!", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();

                                }
                            });

                            snackbar.show();

                        }


                    }
                }
                break;

            case REQUESTPERMISSI:
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        certificado = new AdapterCertificado(getActivity(), list);
                        if (list.size() == 0) {
                            connection.setCompoundDrawables(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.LTGRAY).icon(CommunityMaterial.Icon.cmd_alert_circle), null, null, null);
                            connection.setVisibility(View.VISIBLE);
                            connection.setText("  Verifique sua Conexão com a Internet");
                        } else {
                            recyclerView.setAdapter(certificado);
                            progressBar.setVisibility(View.GONE);
                            certificado.setClick(Certificado.this);
                        }

                    }
                }
                break;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void compra() {

        if(list.get(position).isPago()){


            String url = Config.DOMIONIO + "/php/pdf/pdf.php?certi=854125896589&curso=" + list.get(position).getId() + "&user=" + iduser + "";
            GetDownloads downloads = new GetDownloads(getActivity(), url, "Certificado"
                    , "Certificado do Curso " + list.get(position).getNome(), list.get(position).getNome());
            downloads.downloadManager();


        }else {


            BigDecimal amount = new BigDecimal("39.90");
            Log.i("LOG", amount + "");
            int quantityParcel = 1;
            getPheferencias();
            PagSeguro.pay(new PagSeguroRequest()
                            .withNewItem(list.get(position).getNome(), quantityParcel, amount)
                            .withVendorEmail("carlosaguiar2005@gmail.com")
                            .withBuyerEmail(emailll)
                            .withBuyerCellphoneNumber("55" + tele)
                            .withReferenceCode("123")
                            .withEnvironment(PagSeguro.Environment.PRODUCTION)
                            .withAuthorization("carlosaguiar2005@gmail.com", "C3218244827F4A3C9E645B856097327F"),

                    getActivity(),
                    R.id.relative,
                    new PagSeguro.PagSeguroListener() {
                        @Override
                        public void onSuccess(PagSeguroResponse response, Context context) {

                            Log.i("LOG","PAY"+response);
                            if(response.isSuccess()){

                                String url = Config.DOMIONIO + "/php/pdf/pdf.php?certi=854125896589&curso=" + list.get(position).getId() + "&user=" + iduser + "";
                                GetDownloads downloads = new GetDownloads(getActivity(), url, "Certificado"
                                        , "Certificado do Curso " + list.get(position).getNome(), list.get(position).getNome());
                                downloads.downloadManager();
                            }
                        }

                        @Override
                        public void onFailure(PagSeguroResponse response, Context context) {
                            Log.i("LOG","PAY"+response);
                            Toast.makeText(context, "Lib PS retornou FALHA no pagamento! " + response, Toast.LENGTH_LONG).show();
                        }
                    });






        }




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}

