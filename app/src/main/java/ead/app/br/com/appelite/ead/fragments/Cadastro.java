package ead.app.br.com.appelite.ead.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ead.app.br.com.appelite.ead.AtividadeTabs;
import ead.app.br.com.appelite.ead.R;
import ead.app.br.com.appelite.ead.componets.libs.Mask;
import ead.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.app.br.com.appelite.ead.net.Config;
import ead.app.br.com.appelite.ead.net.volley.Conexao;


/**
 * Created by PC on 08/03/2016.
 */
public class Cadastro extends Fragment implements View.OnClickListener {

    private EditText nomec, username, email, senha, resenha,telefone;
    private ImageButton apagar,buscar;
    private ImageButton casdastra;
    private static final int REQUESTPERMISSI = 49;
    private Bitmap bitmap;
    private ImageView foto;
    private static final int RESQUEST = 11;
    private IconicsDrawable drawable;
    private View view;
    private CheckBox checkBox;
    private ArrayList<String> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cadastro, container, false);

        nomec = (EditText) view.findViewById(R.id.nome);
        username = (EditText) view.findViewById(R.id.username);
        email = (EditText) view.findViewById(R.id.email);
        senha = (EditText) view.findViewById(R.id.senha);
        resenha = (EditText) view.findViewById(R.id.resenha);
        telefone = (EditText) view.findViewById(R.id.telefone);
        foto = (ImageView) view.findViewById(R.id.fotoaluno);



        if(savedInstanceState != null){



            list = (ArrayList<String>) savedInstanceState.getStringArrayList("user");

            nomec.setText(list.get(0));
            username.setText(list.get(1));
            email.setText(list.get(2));
            senha.setText(list.get(3));
            resenha.setText(list.get(4));
            telefone.setText(list.get(5));

        }


        list = new ArrayList<>();

        telefone.addTextChangedListener(Mask.insert("##-#####-####",telefone));

        casdastra = (ImageButton) view.findViewById(R.id.cadastrar);
        buscar = (ImageButton) view.findViewById(R.id.btbuscar);
        apagar = (ImageButton) view.findViewById(R.id.btexcluir);

        casdastra.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.WHITE).icon(FontAwesome.Icon.faw_users));
        buscar.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_search));
        apagar.setImageDrawable(new IconicsDrawable(getActivity()).sizeDp(16).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_close));

        buscar.setOnClickListener(this);
        casdastra.setOnClickListener(this);
        apagar.setOnClickListener(this);





        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resenha.getText().toString().isEmpty()) {
                    if (s.toString().equals(resenha.getText().toString())) {

                      //  resenha.setPrimaryColor(Color.GREEN);
                    } else {
                       // resenha.setPrimaryColor(Color.RED);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!senha.getText().toString().isEmpty()) {
                    if (s.toString().equals(senha.getText().toString())) {

                      //  resenha.setPrimaryColor(Color.GREEN);
                    } else {
                       // resenha.setPrimaryColor(Color.RED);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        drawable = new IconicsDrawable(getActivity());
        drawable.sizeDp(150);
        drawable.color(Color.LTGRAY);
        drawable.icon(FontAwesome.Icon.faw_user);
        bitmap = drawable.toBitmap();
        return (view);
    }

    @Override
    public void onStop() {
        super.onStop();

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
                foto.setImageBitmap(bitmap);
            }
        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUESTPERMISSI:
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {



                        list.add(0,nomec.getText().toString());
                        list.add(1,username.getText().toString());
                        list.add(2,email.getText().toString());
                        list.add(3,senha.getText().toString());
                        list.add(4,resenha.getText().toString());
                        list.add(5,telefone.getText().toString());

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btbuscar:

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {
                        FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSI);
                    }

                } else {



                    list.add(0,nomec.getText().toString());
                    list.add(1,username.getText().toString());
                    list.add(2,email.getText().toString());
                    list.add(3,senha.getText().toString());
                    list.add(4,resenha.getText().toString());
                    list.add(5,telefone.getText().toString());

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, RESQUEST);


                }
                break;

            case R.id.cadastrar:

                if (!nomec.getText().toString().isEmpty() && !username.getText().toString().isEmpty() &&
                        !email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty() &&
                        !resenha.getText().toString().isEmpty() && senha.getText().toString().equals(resenha.getText().toString())) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                            .title("Confirmar")
                            .positiveText("Sim")
                            .negativeText("Não")
                            .positiveColor(Color.GREEN)
                            .negativeColor(Color.RED)
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            }).onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                    progressDialog.setIndeterminate(true);
                                    progressDialog.setMessage("Carregando...");
                                    progressDialog.show();
                                    HashMap<String, String> map = new HashMap<String, String>();

                                    map.put("nomec", nomec.getText().toString());
                                    map.put("user", username.getText().toString());
                                    map.put("email", email.getText().toString());
                                    map.put("tele", Mask.unmask( telefone.getText().toString()));
                                    map.put("senha", senha.getText().toString());
                                    map.put("foto", getStringImage(bitmap));
                                    map.put("metodo", "cadastro");


                                    Conexao.Conexao(getActivity(), Config.DOMIONIO+"/php/request/cadas_login_app.php", map, new DadosVolley() {
                                        @Override
                                        public void geJsonObject(JSONObject jsonObject) {

                                            Log.i("LOG",jsonObject+"");
                                            try {
                                                if(jsonObject.has("condicao") == true){
                                                    if(jsonObject.getBoolean("condicao") == true){
                                                        progressDialog.dismiss();
                                                        Snackbar.make(view,"Cadastrado com Sucesso",Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                                            @Override
                                                            public void onDismissed(Snackbar snackbar, int event) {
                                                                super.onDismissed(snackbar, event);
                                                              //  getActivity().getFragmentManager().beginTransaction().remove(Cadastro.this).commit();
                                                                startActivity(new Intent(getActivity(), AtividadeTabs.class));
                                                                getActivity().finish();

                                                            }
                                                        }).show();


                                                    }
                                                }else if(jsonObject.has("dupla")){
                                                    if(jsonObject.getBoolean("dupla") == true){
                                                        progressDialog.dismiss();
                                                        Snackbar.make(view,"E-mail Existente",Snackbar.LENGTH_SHORT).show();

                                                    }
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void ErrorVolley(String messege) {
                                            progressDialog.dismiss();
                                            Log.i("LOG",messege);

                                        }
                                    });
                                }
                            });

                        MaterialDialog dialog = builder.build();
                    dialog.show();

                } else {
                    Snackbar.make(view, "Verifique Seus Dados Pessoais", Snackbar.LENGTH_SHORT).show();

                }
                break;

            case R.id.btexcluir:
                bitmap = drawable.toBitmap();
                foto.setImageBitmap(bitmap);


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

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();


        list.add(0,nomec.getText().toString());
        list.add(1,username.getText().toString());
        list.add(2,email.getText().toString());
        list.add(3,senha.getText().toString());
        list.add(4,resenha.getText().toString());
        list.add(5,telefone.getText().toString());

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("user",list);

    }
}
