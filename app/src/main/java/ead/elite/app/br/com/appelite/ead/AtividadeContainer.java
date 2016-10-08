package ead.elite.app.br.com.appelite.ead;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.bd.Database;
import ead.elite.app.br.com.appelite.ead.bd.NetDados;
import ead.elite.app.br.com.appelite.ead.componets.libs.MensageIcon;
import ead.elite.app.br.com.appelite.ead.fragments.Cadastro;
import ead.elite.app.br.com.appelite.ead.fragments.CompraPag;
import ead.elite.app.br.com.appelite.ead.fragments.Mensagem;
import ead.elite.app.br.com.appelite.ead.fragments.MeusCursos;
import ead.elite.app.br.com.appelite.ead.fragments.Perfil;


public class AtividadeContainer extends AppCompatActivity {
    private Toolbar toolbar;


    private Dados dados;
    private int face = 0;
    private MensageIcon mensageIcon;
    private String email, nome, urlfato, iddevice;
    private boolean estado;
    private Fragment frag;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_atividade_container);

        dados = new Dados(AtividadeContainer.this);


        mensageIcon = (MensageIcon) findViewById(R.id.masage);
        mensageIcon.setBackgroundDrawable(new IconicsDrawable(AtividadeContainer.this).color(Color.WHITE).icon(FontAwesome.Icon.faw_envelope).sizeDp(24));
        mensageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensageIcon.hide();
                Mensagem frag = null;
                if (frag == null) {

                    frag = new Mensagem();
                    FragmentTransaction manager = getFragmentManager().beginTransaction();
                    manager.replace(R.id.relativ, frag, "fraf");
                    manager.addToBackStack("p");
                    manager.commit();


                }
            }
        });


        getPheferencias();
        final NotificationManager manage = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manage.cancelAll();

        //


        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        //ID
        toolbar = (Toolbar) findViewById(R.id.toobarPai);
        toolbar.setBackgroundColor(Color.parseColor("#039be5"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);








      /*  SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MINUTE, 30);
        Date data_atual = cal.getTime();


        String data_completa = dateFormat_hora.format(data_atual);

        String hora_atual = dateFormat_hora.format(data_atual); */

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });


        //   Log.i("hora_atual", hora_atual); // Esse é o que você quer  */

        //NAVIGATION




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();


        getmensagem();

        int fragg = getIntent().getIntExtra("fra", 0);
        FragmentTransaction manager = getFragmentManager().beginTransaction();

        switch (fragg) {

            case 2:

                frag = new Cadastro();
                manager.replace(R.id.relativ, frag);
                manager.commit();
                toolbar.setSubtitle("Cadastro de Usuarios");

                break;

            case 3:

                frag = new Mensagem();
                manager.replace(R.id.relativ, frag);
                manager.commit();
                toolbar.setSubtitle("Caixa de Entrada");

                break;

            case 4:
                frag = (Perfil) getFragmentManager().findFragmentByTag("per");
                if(frag == null){
                    frag = new Perfil();
                    manager.replace(R.id.relativ, frag, "per");
                    manager.commit();
                    toolbar.setSubtitle("Perfil do Usuario");
                }


                break;

            case 5:

                frag = new CompraPag();
                manager.replace(R.id.relativ, frag);
                manager.commit();
                toolbar.setSubtitle("Compras");

                break;

            case 0 : finish(); break;
        }



    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void getmensagem() {

        Database database = new Database(AtividadeContainer.this);
        int i = database.getLeitura();
        if (i > 0) {
            mensageIcon.show(" " + i);
        } else if (i == 0) {
            mensageIcon.hide();
        }


    }

    public void getPheferencias() {
        SharedPreferences preferences = getSharedPreferences(Dados.LOGIN_NAME, MODE_PRIVATE);
        nome = preferences.getString("1452", "");
        email = preferences.getString("245", "");
        urlfato = preferences.getString("1454", "");
        estado = preferences.getBoolean("12", estado);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
