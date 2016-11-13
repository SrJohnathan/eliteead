package ead.elite.app.br.com.appelite.ead;

import android.app.Fragment;
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
import android.view.View;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ead.elite.app.br.com.appelite.ead.aplication.App;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.bd.Database;
import ead.elite.app.br.com.appelite.ead.bd.NetDados;
import ead.elite.app.br.com.appelite.ead.componets.libs.MensageIcon;
import ead.elite.app.br.com.appelite.ead.fragments.Cadastro;
import ead.elite.app.br.com.appelite.ead.fragments.Certificado;
import ead.elite.app.br.com.appelite.ead.fragments.CompraPag;
import ead.elite.app.br.com.appelite.ead.fragments.Mensagem;
import ead.elite.app.br.com.appelite.ead.fragments.MeusCursos;
import ead.elite.app.br.com.appelite.ead.fragments.Perfil;
import ead.elite.app.br.com.appelite.ead.util.IabHelper;


public class AtividadePai extends AppCompatActivity {
    private Toolbar toolbar;
    private Drawer drawer;
    private AccountHeader header;
    private LoginManager loginManager;
    private View view;
    private ProfileDrawerItem profileDrawerItem;
    private Dados dados;
    private int face = 0;
    private MensageIcon mensageIcon;
    private String email, nome, urlfato, iddevice;
    private boolean estado;
    private PrimaryDrawerItem item1, item2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_atividade_pai);

        dados = new Dados(AtividadePai.this);


        mensageIcon = (MensageIcon) findViewById(R.id.masage);
        mensageIcon.setBackgroundDrawable(new IconicsDrawable(AtividadePai.this).color(Color.WHITE).icon(FontAwesome.Icon.faw_envelope).sizeDp(24));
        mensageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensageIcon.hide();
                Mensagem frag = null;
                if (frag == null) {

                    frag = new Mensagem();
                    FragmentTransaction manager = getFragmentManager().beginTransaction();
                    manager.replace(R.id.relative, frag, "fraf");
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
        header = new AccountHeaderBuilder()
                .withSavedInstance(savedInstanceState)
                .withTextColor(Color.WHITE)
                .withHeaderBackground(R.drawable.sombra)
                .withActivity(this).build();

        profileDrawerItem = new ProfileDrawerItem().withEmail("Email do Usuario").withName("Nome do Usuario");
        header.addProfiles(profileDrawerItem);


        if (!nome.isEmpty()) {
            profileDrawerItem.withName(nome);
            profileDrawerItem.withEmail(email);
            profileDrawerItem.withIcon(urlfato);
            header.updateProfile(profileDrawerItem);

        }

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withSliderBackgroundColor(getResources().getColor(R.color.md_white_1000))
                .withStatusBarColor(Color.BLACK)
                .withToolbar(toolbar)
                .withSelectedItem(5)
                .withAccountHeader(header)
                .withFullscreen(true)
                .withSavedInstance(savedInstanceState)
                .build();


        item1 = new PrimaryDrawerItem().withBadge("32").withBadgeStyle(new BadgeStyle(Color.RED, Color.WHITE)).withName("Todos os Cursos").withIcon(
                new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_book).sizeDp(30).color(Color.LTGRAY));
        item2 = new PrimaryDrawerItem().withBadge("12").withBadgeStyle(new BadgeStyle(Color.RED, Color.WHITE)).withName("Meus Cursos").withIcon(
                new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_book_image).sizeDp(30).color(Color.LTGRAY));


        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Certificados").withIcon(
                new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_certificate).sizeDp(30).color(Color.LTGRAY));
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Perfil").withIcon(
                new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_folder_person).sizeDp(30).color(Color.LTGRAY));


        drawer.addItems(new SectionDrawerItem().withName("Conteúdo").withDivider(false).withTextColor(Color.GRAY), item1, item2, item3,
                new SectionDrawerItem().withName("Configurações").withDivider(true).withTextColor(Color.GRAY), item4);

        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(final View view, int position, IDrawerItem drawerItem) {
                Fragment frag = null;
                FragmentTransaction manager = getFragmentManager().beginTransaction();

                switch (position) {
                    case 2:


                        Intent intentt = new Intent(AtividadePai.this, AtividadeTabs.class);
                        startActivity(intentt);
                        finish();
                        break;

                    case 3:

                        frag = new MeusCursos();
                        manager.replace(R.id.relative, frag, "hh");
                        manager.commit();
                        toolbar.setSubtitle("Meus Cursos");
                        break;
                    case 4:
                        frag = new Certificado();
                        manager.replace(R.id.relative, frag, "hh");
                        manager.addToBackStack("j");
                        manager.commit();
                        toolbar.setSubtitle("Certificados");
                        break;

                    case 6:

                        Intent intent = new Intent(AtividadePai.this, AtividadeContainer.class);
                        intent.putExtra("fra", 4);
                        startActivity(intent);
                        break;
                }


                return false;
            }
        });



    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();


        }


    }

    @Override
    protected void onStart() {
        super.onStart();


        if (NetDados.getPheferencias(AtividadePai.this, "146").isEmpty()) {
            item1.withBadge("0");

        } else {
            item1.withBadge(NetDados.getPheferencias(AtividadePai.this, "146"));
        }


        if (NetDados.getPheferencias(AtividadePai.this, "147").isEmpty()) {
            item2.withBadge("0");

        } else {
            item2.withBadge(NetDados.getPheferencias(AtividadePai.this, "147"));
        }

        getmensagem();

        int fragg = getIntent().getIntExtra("frag", 0);
        Fragment frag = null;
        FragmentTransaction manager = getFragmentManager().beginTransaction();
        switch (fragg) {
            case 1:
                frag = (MeusCursos) getFragmentManager().findFragmentByTag("meus");
                if(frag == null) {
                    frag = new MeusCursos();
                    manager.replace(R.id.relative, frag,"meus");
                    manager.commit();
                    toolbar.setSubtitle("Meus Cursos");
                }
                break;


            case 3:

                frag = new Mensagem();
                manager.replace(R.id.relative, frag);
                manager.commit();
                toolbar.setSubtitle("Caixa de Entrada");

                break;

            case 0:
                finish();
                break;
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
        outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void getmensagem() {

        Database database = new Database(AtividadePai.this);
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


}
