package ead.elite.app.br.com.appelite.ead.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.dominio.ProgressText;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;

/**
 * Created by Pc on 14/09/2016.
 */
public class Perfil extends Fragment implements View.OnClickListener {

    private View view;
    private ImageButton btnome, btsolho, btsenha, bttele, btuser, btemail, btbuscar, btesclu;
    private EditText etnome, etuser, etsenha, ettele, etemail;
    private static final int REQUESTPERMISSI = 59;
    private static final int RESQUEST = 12;
    private ImageView foto;
    private Bitmap bitmap;
    private int iduser;
    private MaterialDialog progressDialog;
    private String email, nome, urlfato, senha, fotor;
    private boolean estado;

    private ProgressBar proimg;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, " Impossível de alterar dados", Snackbar.LENGTH_SHORT).show();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.perfil, container, false);

        final Context context = getActivity().getApplicationContext();

        foto = (ImageView) view.findViewById(R.id.fotoaluno);

        proimg = (ProgressBar) view.findViewById(R.id.proimg);

        btnome = (ImageButton) view.findViewById(R.id.btnome);
        btsolho = (ImageButton) view.findViewById(R.id.btsolho);
        btsenha = (ImageButton) view.findViewById(R.id.btsenha);
        btemail = (ImageButton) view.findViewById(R.id.btemail);
        bttele = (ImageButton) view.findViewById(R.id.bttele);
        btuser = (ImageButton) view.findViewById(R.id.btuser);
        btbuscar = (ImageButton) view.findViewById(R.id.btbuscar);
        btesclu = (ImageButton) view.findViewById(R.id.btexclu);

        etnome = (EditText) view.findViewById(R.id.etnome);
        etemail = (EditText) view.findViewById(R.id.etemail);
        etuser = (EditText) view.findViewById(R.id.etuser);
        ettele = (EditText) view.findViewById(R.id.ettele);
        etsenha = (EditText) view.findViewById(R.id.etsenha);

        EventBus.getDefault().register(this);

        btesclu.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_close));
        btbuscar.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_search));
        btnome.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_content_save).color(Color.WHITE).sizeDp(16));
        btsolho.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_eye).color(Color.WHITE).sizeDp(16));
        btsenha.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_content_save).color(Color.WHITE).sizeDp(16));
        bttele.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_content_save).color(Color.WHITE).sizeDp(16));
        btuser.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_content_save).color(Color.WHITE).sizeDp(16));
        btemail.setImageDrawable(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_content_save).color(Color.WHITE).sizeDp(16));


        etnome.setCompoundDrawables(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_account).color(Color.LTGRAY).sizeDp(24), null, null, null);
        etnome.setCompoundDrawablePadding(5);

        etemail.setCompoundDrawables(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_email_secure).color(Color.LTGRAY).sizeDp(24), null, null, null);
        etemail.setCompoundDrawablePadding(5);

        etuser.setCompoundDrawables(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_account_star_variant).color(Color.LTGRAY).sizeDp(24), null, null, null);
        etuser.setCompoundDrawablePadding(5);

        ettele.setCompoundDrawables(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_cellphone).color(Color.LTGRAY).sizeDp(24), null, null, null);
        ettele.setCompoundDrawablePadding(5);

        etsenha.setCompoundDrawables(new IconicsDrawable(getActivity()).icon(CommunityMaterial.Icon.cmd_key).color(Color.LTGRAY).sizeDp(24), null, null, null);
        etsenha.setCompoundDrawablePadding(5);


        proimg.setIndeterminate(true);

        getPheferencias();


        HashMap<String, String> map = new HashMap<>();
        map.put("id", iduser + "");
        map.put("metodo", "aluno");

        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Atualização")
                .content("Carregando...")
                .progress(true, 0).build();
        progressDialog.show();


        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/perfil.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {

                try {
                    JSONObject object = jsonObject.getJSONObject("0");


                    if (!object.getString("idface").equals("null")) {
                        getPheferencias();
                        String nomee = object.getString("nome_completo").equals("null") ? "" : object.getString("nome_completo");
                        String teler = object.getString("tele").equals("null") ? "" : object.getString("tele");

                        etnome.setText(nomee);
                        ettele.setText(teler);


                        etuser.setText(nome);
                        etemail.setText(object.getString("email"));
                        etsenha.setText("........");
                        etemail.setEnabled(false);
                        etuser.setEnabled(false);
                        etsenha.setEnabled(false);
                        Picasso.with(context).load(urlfato).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                foto.setImageBitmap(bitmap);
                                proimg.setVisibility(View.GONE);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                        btbuscar.setOnClickListener(onClickListener);
                        btesclu.setOnClickListener(onClickListener);
                        btuser.setOnClickListener(onClickListener);
                        btesclu.setOnClickListener(onClickListener);
                        btsenha.setOnClickListener(onClickListener);
                        btsolho.setOnClickListener(onClickListener);
                        btemail.setOnClickListener(onClickListener);
                        btnome.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                updateface("nome_completo", iduser + "", etnome.getText().toString(), null);
                            }
                        });
                        bttele.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateface("tele", iduser + "", ettele.getText().toString(), null);

                            }
                        });

                        progressDialog.hide();


                    } else {
                        senha = object.getString("senha");
                        etnome.setText(object.getString("nome_completo"));
                        ettele.setText(object.getString("tele"));
                        etuser.setText(object.getString("username"));
                        etemail.setText(object.getString("email"));
                        etsenha.setText(object.getString("senha"));
                        Picasso.with(context).load(Config.DOMIONIO + "/php/server/image.php?metodo=aluno&id=" + iduser).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                foto.setImageBitmap(bitmap);
                                proimg.setVisibility(View.GONE);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                        btbuscar.setOnClickListener(Perfil.this);
                        btesclu.setOnClickListener(Perfil.this);
                        btuser.setOnClickListener(Perfil.this);
                        btesclu.setOnClickListener(Perfil.this);
                        btsenha.setOnClickListener(Perfil.this);
                        btsolho.setOnClickListener(Perfil.this);
                        btemail.setOnClickListener(Perfil.this);
                        btnome.setOnClickListener(Perfil.this);
                        bttele.setOnClickListener(Perfil.this);
                        progressDialog.hide();
                        btbuscar.setOnClickListener(Perfil.this);
                        etsenha.setEnabled(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void ErrorVolley(String messege) {
                Snackbar.make(view, "Erro na conexão", Snackbar.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri uri = data.getData();
                String[] coluna = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, coluna, null, null, null);
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(coluna[0]);
                String path = cursor.getString(index);
                cursor.close();
                bitmap = BitmapFactory.decodeFile(path);


                Bundle bundle = new Bundle();



                Intent intent = new Intent("SERVICO_FOTO");
                intent.putExtra("user",iduser);
                intent.putExtra("img",getStringImage(bitmap));
                getActivity().startService(intent);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUESTPERMISSI:
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/jpeg");
                        startActivityForResult(intent, RESQUEST);
                    }
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View vie) {

        switch (vie.getId()) {
            case R.id.btbuscar:

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {
                        FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSI);
                    }

                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, RESQUEST);
                }
                break;
            case R.id.btnome:
                update("nome_completo", iduser + "", etnome.getText().toString(), new SaidaSnack() {
                    @Override
                    public void Saida() {

                    }
                });

                break;
            case R.id.bttele:
                update("tele", iduser + "", ettele.getText().toString(), new SaidaSnack() {
                    @Override
                    public void Saida() {

                    }
                });

                break;
            case R.id.btuser:
                update("username", iduser + "", etuser.getText().toString(), new SaidaSnack() {
                    @Override
                    public void Saida() {

                    }
                });

                break;
            case R.id.btemail:
                update("email", iduser + "", etemail.getText().toString(), new SaidaSnack() {
                    @Override
                    public void Saida() {

                    }
                });

                break;
            case R.id.btsenha:
                if (etsenha.isEnabled()) {
                    update("senha", iduser + "", etsenha.getText().toString(), new SaidaSnack() {
                        @Override
                        public void Saida() {

                            getActivity().getFragmentManager().beginTransaction().remove(Perfil.this).commit();
                        }
                    });
                } else {
                    Snackbar.make(view, "Destrave á Edição", Snackbar.LENGTH_SHORT).show();
                }


                break;
            case R.id.btsolho:


                new MaterialDialog.Builder(getActivity()).title("Atualização").content("Informe sua Senha.").input("Senha", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.toString().equals(senha)) {
                            dialog.hide();
                            etsenha.setEnabled(true);
                        } else {
                            Toast.makeText(getActivity(), "Senha Incorreta!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();


                break;
        }
    }

    public String getStringImage(Bitmap bmp) {
        bmp = Bitmap.createScaledBitmap(bmp, 120, 120, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void getPheferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Dados.LOGIN_NAME, Context.MODE_PRIVATE);
        nome = preferences.getString("1452", "");
        email = preferences.getString("245", "");
        iduser = preferences.getInt("2454", iduser);
        urlfato = preferences.getString("1454", "");
        estado = preferences.getBoolean("12", estado);
    }


    private void update(final String coluna, final String user, final String value, final Perfil.SaidaSnack snack) {

        final String[] sen = new String[1];

        MaterialDialog builder = new MaterialDialog.Builder(getActivity())
                .title("Atualização")
                .content("Informe sua Senha.")
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input("Senha", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.toString().equals(senha)) {
                            final MaterialDialog bl = new MaterialDialog.Builder(getActivity())
                                    .title("Atualização")
                                    .content("Carregando...")
                                    .progress(true, 0)
                                    .show();

                            HashMap<String, String> map = new HashMap<>();
                            map.put("metodo", "update");
                            map.put("coluna", coluna);
                            map.put("id", user);
                            map.put("value", value);
                            Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/perfil.php", map, new DadosVolley() {
                                @Override
                                public void geJsonObject(JSONObject jsonObject) {

                                    if (jsonObject.has("res")) {
                                        try {
                                            if (jsonObject.getString("res").equals("update")) {
                                                bl.hide();

                                                Snackbar.make(view, "Atualizado com sucesso !", Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {
                                                        super.onDismissed(snackbar, event);
                                                        snack.Saida();
                                                    }

                                                    @Override
                                                    public void onShown(Snackbar snackbar) {
                                                        super.onShown(snackbar);
                                                    }
                                                }).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }

                                @Override
                                public void ErrorVolley(String messege) {
                                    bl.hide();
                                    Snackbar.make(view, "Erro na conexão", Snackbar.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                }).show();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    private interface SaidaSnack {
        public void Saida();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void updateface(final String coluna, final String user, final String value, final Perfil.SaidaSnack snack) {




        final MaterialDialog bl = new MaterialDialog.Builder(getActivity())
                .title("Atualização")
                .content("Carregando...")
                .progress(true, 0)
                .show();

        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "update");
        map.put("coluna", coluna);
        map.put("id", user);
        map.put("value", value);
        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/perfil.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {

                if (jsonObject.has("res")) {
                    try {
                        if (jsonObject.getString("res").equals("update")) {
                            bl.hide();

                            Snackbar.make(view, "Atualizado com sucesso !", Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    super.onDismissed(snackbar, event);
                                    snack.Saida();
                                }

                                @Override
                                public void onShown(Snackbar snackbar) {
                                    super.onShown(snackbar);
                                }
                            }).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void ErrorVolley(String messege) {
                bl.hide();
                Snackbar.make(view, "Erro na conexão", Snackbar.LENGTH_SHORT).show();

            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProgressText progressText){

        if(progressText.getNameClass().equals("servicofoot")){
            foto.setImageBitmap(bitmap);
        }

    }
}
