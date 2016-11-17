package ead.elite.app.br.com.appelite.ead;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ead.elite.app.br.com.appelite.ead.bd.CoreId;
import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.bd.Database;
import ead.elite.app.br.com.appelite.ead.bd.NetDados;
import ead.elite.app.br.com.appelite.ead.componets.AnomationFab;
import ead.elite.app.br.com.appelite.ead.componets.libs.MensageIcon;
import ead.elite.app.br.com.appelite.ead.fragments.Tabs;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;
import ead.elite.app.br.com.appelite.ead.services.PrograssService;


public class AtividadeTabs extends AppCompatActivity {
    private Toolbar toolbar;
    private Drawer drawer;
    private AccountHeader header;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private View view;
    private ProfileDrawerItem profileDrawerItem;
    private Dados dados;
    private AlertDialog dialogview;
    private MensageIcon mensageIcon;
    private String email, nome, urlfato, iddevice;
    private boolean estado;
    public static boolean esta = true;
    private PrimaryDrawerItem item1, item2, cadas;
    private SliderLayout slidingTabLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageButton imageButton;
    private FloatingActionButton menufab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Fresco.initialize(this);

        if (isServiceRunning(PrograssService.class.getName() + "")) {
            stopService(new Intent("SERVICO_CO"));
        }


        setContentView(R.layout.activity_atividade_tabs);
        callbackManager = CallbackManager.Factory.create();
        dados = new Dados(AtividadeTabs.this);
        menufab = (FloatingActionButton) findViewById(R.id.menufab);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        textView = (TextView) findViewById(R.id.error);
        imageButton = (ImageButton) findViewById(R.id.btexcluir);
        mensageIcon = (MensageIcon) findViewById(R.id.masage);


        imageButton.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_reload).sizeDp(24).color(Color.BLACK));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading();
            }
        });


        mensageIcon.setBackgroundDrawable(new IconicsDrawable(AtividadeTabs.this).color(Color.WHITE).icon(FontAwesome.Icon.faw_envelope).sizeDp(24));
        mensageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensageIcon.hide();

                Intent intent = new Intent(AtividadeTabs.this, AtividadeContainer.class);
                intent.putExtra("fra", 3);
                startActivity(intent);

            }
        });


        getPheferencias();
        final NotificationManager manage = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manage.cancelAll();


     /*   if (estado) {
            if(savedInstanceState != null){
                esta = (boolean) savedInstanceState.getBoolean("esta");

            }else {
                if(esta == true){
                    esta = false;
                    Intent intentt = new Intent(this, AtividadePai.class);
                    intentt.putExtra("frag", 1);
                    startActivity(intentt);
                }

            }
        } */


        //ID
        toolbar = (Toolbar) findViewById(R.id.toobarPai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView.setCompoundDrawables(new IconicsDrawable(this).sizeDp(30).color(Color.LTGRAY).icon(CommunityMaterial.Icon.cmd_alert_circle), null, null, null);


        //PARAMETROS


        //  if (savedInstanceState == null) {

        slidingTabLayout = (SliderLayout) findViewById(R.id.slider);
        slidingTabLayout.setDuration(10000);
        slidingTabLayout.setSliderTransformDuration(2000, null);


        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "getslider");
        Conexao.Conexao(this, Config.DOMIONIO + "/php/server/galeria.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {

                try {
                    final JSONObject object = jsonObject.getJSONObject("0");
                    DefaultSliderView sliderView1 = new DefaultSliderView(AtividadeTabs.this);
                    sliderView1.image(object.getString("foto_slider"));
                    sliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            try {
                                intent.setData(Uri.parse(object.getString("url")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        }
                    });
                    slidingTabLayout.addSlider(sliderView1);

                    final JSONObject object2 = jsonObject.getJSONObject("1");
                    DefaultSliderView sliderView2 = new DefaultSliderView(AtividadeTabs.this);
                    sliderView2.image(object2.getString("foto_slider"));
                    sliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            try {
                                intent.setData(Uri.parse(object2.getString("url")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        }
                    });
                    slidingTabLayout.addSlider(sliderView2);

                    final JSONObject object3 = jsonObject.getJSONObject("2");
                    DefaultSliderView sliderView3 = new DefaultSliderView(AtividadeTabs.this);
                    sliderView3.image(object3.getString("foto_slider"));
                    sliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            try {
                                intent.setData(Uri.parse(object3.getString("url")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        }
                    });
                    slidingTabLayout.addSlider(sliderView3);


                    Log.i("LOG", object + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void ErrorVolley(String messege) {

            }
        });

        menufab.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_reload).sizeDp(24).color(Color.WHITE));
        menufab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loading();

            }
        });

        viewPager = (ViewPager) findViewById(R.id.view);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.md_light_blue_600));

        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                AnomationFab.animateFab(AtividadeTabs.this, tab.getPosition(), menufab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        loading();

        //  }







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
                .withCompactStyle(true)
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

        final PrimaryDrawerItem login = new PrimaryDrawerItem().withName("Login").withIcon(
                new IconicsDrawable(this).icon(FontAwesome.Icon.faw_user).sizeDp(30).color(getResources().getColor(R.color.md_grey_600))).withIdentifier(158);


        cadas = new PrimaryDrawerItem().withName("Cadastro").withIcon(
                new IconicsDrawable(this).icon(FontAwesome.Icon.faw_users).sizeDp(30).color(getResources().getColor(R.color.md_grey_600))).withIdentifier(159);

        item1 = new PrimaryDrawerItem().withBadge("32").withBadgeStyle(new BadgeStyle(Color.RED, Color.WHITE)).withName("Todos os Cursos").withIcon(
                new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_book).sizeDp(30).color(Color.LTGRAY));
        item2 = new PrimaryDrawerItem().withBadge("12").withBadgeStyle(new BadgeStyle(Color.RED, Color.WHITE)).withName("Meus Cursos").withIcon(
                new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_book_image).sizeDp(30).color(Color.LTGRAY));


        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withName("Sobre").withIcon(
                new IconicsDrawable(this).icon(FontAwesome.Icon.faw_wrench).sizeDp(30).color(Color.LTGRAY));
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withName("Facebook").withIcon(
                new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_facebook_box).sizeDp(30).color(Color.LTGRAY));
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withName("Site").withIcon(
                new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_google_chrome).sizeDp(30).color(Color.LTGRAY));

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withSliderBackgroundColor(getResources().getColor(R.color.md_white_1000))
                .withStatusBarColor(Color.BLACK)
                .withToolbar(toolbar)
                .withSelectedItem(5)
                .withAccountHeader(header)
                .withFullscreen(true)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(new SectionDrawerItem().withName("Conteúdo").withDivider(true).withTextColor(Color.GRAY), item1, item2,
                        new SectionDrawerItem().withName("Redes Sociais").withDivider(true).withTextColor(Color.GRAY), item6, item7, new SectionDrawerItem().withName("Configurações").withDivider(true).withTextColor(Color.GRAY), item5)
                .addStickyDrawerItems(login)
                .withHasStableIds(true)
                .build();

        if (estado == true) {
            drawer.removeItem(159);

        } else {
            drawer.addStickyFooterItem(cadas);
        }

        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(final View view, int position, IDrawerItem drawerItem) {


                Fragment fragment = null;
                FragmentTransaction manager = getFragmentManager().beginTransaction();

                if (drawerItem.getIdentifier() == 159) {
                    drawer.closeDrawer();
                    cadastro();
                } else if (drawerItem.getIdentifier() == 158) {
                    drawer.closeDrawer();
                    loginApp();

                }

                switch (position) {

                    case 2:
                        viewPager.setCurrentItem(0);

                        break;
                    case 3:
                        if (estado == true) {
                            Intent intent = new Intent(AtividadeTabs.this, AtividadePai.class);
                            intent.putExtra("frag", 1);

                            startActivity(intent);
                        } else {
                            Snackbar.make(toolbar, " Você precisa estar logado", Snackbar.LENGTH_SHORT).show();

                        }


                        break;

                    case 8:
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(AtividadeTabs.this)
                                .customView(R.layout.sobre, true);

                        MaterialDialog dialog = builder.build();
                        dialog.show();


                        break;
                    case 5:

                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse("https://www.facebook.com/elitecursossobral/?fref=ts"));
                        startActivity(intent1);
                        break;

                    case 6:

                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        intent2.setData(Uri.parse("http://www.eliteeducacao.com.br"));
                        startActivity(intent2);
                        break;

                    case 14:


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


        if (NetDados.getPheferencias(AtividadeTabs.this, "146").isEmpty()) {
            item1.withBadge("0");

        } else {
            item1.withBadge(NetDados.getPheferencias(AtividadeTabs.this, "146"));
        }


        if (NetDados.getPheferencias(AtividadeTabs.this, "147").isEmpty()) {
            item2.withBadge("0");

        } else {
            item2.withBadge(NetDados.getPheferencias(AtividadeTabs.this, "147"));
        }

        getmensagem();


    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem logmenu = menu.findItem(R.id.logmenu);
        if (estado) {
            logmenu.setIcon(new IconicsDrawable(AtividadeTabs.this).icon(FontAwesome.Icon.faw_sign_in).color(Color.WHITE).sizeDp(24));

        } else {
            logmenu.setIcon(new IconicsDrawable(AtividadeTabs.this).icon(FontAwesome.Icon.faw_sign_out).color(Color.WHITE).sizeDp(24));

        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logmenu:
                loginApp();
                break;
        }


        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = drawer.saveInstanceState(outState);
        outState.putBoolean("esta", esta);
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void LoginFacebook() {

        loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(AtividadeTabs.this, Arrays.asList("email", "public_profile"));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,name,last_name,email,picture");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            final Profile profile1 = new Profile(response.getJSONObject().getString("id"), null, null, null, null, null);
                            final String email = response.getJSONObject().getString("email");
                            final String id = response.getJSONObject().getString("id");
                            final String name = response.getJSONObject().getString("name");
                            JSONObject obg = response.getJSONObject();
                            final String urll = obg.getJSONObject("picture").getJSONObject("data").getString("url");
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("metodo", "facebook");
                            map.put("idface", id);
                            map.put("email", email);
                            map.put("token", CoreId.getPheferencias(AtividadeTabs.this, "5nn"));

                            Conexao.Conexao(AtividadeTabs.this, Config.DOMIONIO + "/php/request/login_cadas.php", map, new DadosVolley() {
                                @Override
                                public void geJsonObject(JSONObject jsonObject) {
                                    Log.i("LOG", "" + jsonObject);
                                    try {
                                        if (jsonObject.has("resposta") == true) {
                                            int idaluno = jsonObject.getInt("idaluno");

                                            profileDrawerItem.withIcon(profile1.getProfilePictureUri(100, 100).toString());
                                            profileDrawerItem.withName(name);
                                            profileDrawerItem.withEmail(email);
                                            header.updateProfile(profileDrawerItem);

                                            dados.setDados(idaluno, email, name, "", id, profile1.getProfilePictureUri(100, 100).toString(), true);
                                            dados.Commit();
                                            estado = true;
                                            NetDados netDados = new NetDados(AtividadeTabs.this);
                                            netDados.Clear();
                                            netDados.setDados("258", "true");
                                            netDados.Commit();
                                            drawer.removeItem(159);
                                            drawer.resetDrawerContent();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void ErrorVolley(String messege) {
                                    Snackbar.make(view, "Verifique sua Conexão com a Internet", Snackbar.LENGTH_SHORT).show();

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                request.setParameters(bundle);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    public void getmensagem() {

        Database database = new Database(AtividadeTabs.this);
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

    public void loginApp() {


        getPheferencias();
        if (estado == false) {


            view = View.inflate(AtividadeTabs.this, R.layout.login, null);
            LinearLayout btface = (LinearLayout) view.findViewById(R.id.fb_login_btn);
            LinearLayout btlogin = (LinearLayout) view.findViewById(R.id.login_btn);


            final EditText email = (EditText) view.findViewById(R.id.email);
            email.setCompoundDrawables(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_account).color(Color.LTGRAY).sizeDp(24), null, null, null);
            email.setCompoundDrawablePadding(5);

            final EditText senha = (EditText) view.findViewById(R.id.senha);
            senha.setCompoundDrawables(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_key).color(Color.LTGRAY).sizeDp(24), null, null, null);
            senha.setCompoundDrawablePadding(5);

            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            Drawable d = new ColorDrawable(Color.BLACK);
            AlertDialog.Builder dialogB = new AlertDialog.Builder(AtividadeTabs.this).setView(view);
            AlertDialog dialog = dialogB.create();
            d.setAlpha(0);
            dialog.getWindow().setBackgroundDrawable(d);

            btlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vie) {

                    if (!email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()) {

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("metodo", "login");
                        map.put("token", CoreId.getPheferencias(AtividadeTabs.this, "5nn"));
                        map.put("email", email.getText().toString());
                        map.put("senha", senha.getText().toString());
                        dialogview.dismiss();
                        final ProgressDialog progressDialog = new ProgressDialog(AtividadeTabs.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Carregando...");
                        progressDialog.show();

                        Conexao.Conexao(AtividadeTabs.this, Config.DOMIONIO + "/php/request/cadas_login_app.php", map, new DadosVolley() {
                            @Override
                            public void geJsonObject(JSONObject jsonObject) {


                                try {
                                    JSONObject object = jsonObject.getJSONObject("0");

                                    int id = object.getInt("idaluno");
                                    String user = object.getString("username");
                                    String email = object.getString("email");

                                    dados.setDados(id, email, user, null, null, Config.DOMIONIO + "/php/server/image.php?metodo=aluno&id=" + id, true);
                                    dados.Commit();
                                    estado = true;
                                    NetDados netDados = new NetDados(AtividadeTabs.this);
                                    netDados.Clear();
                                    netDados.setDados("258", "false");
                                    netDados.Commit();
                                    profileDrawerItem.withIcon(Config.DOMIONIO + "/php/server/image.php?metodo=aluno&id=" + id);
                                    profileDrawerItem.withName(user);
                                    profileDrawerItem.withEmail(email);
                                    header.updateProfile(profileDrawerItem);
                                    drawer.removeItem(159);
                                    drawer.resetDrawerContent();
                                    progressDialog.hide();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void ErrorVolley(String messege) {
                                Snackbar.make(toolbar, "Verifique sua Conexão com a Internet", Snackbar.LENGTH_SHORT).show();
                                Log.i("LOG",messege);

                            }
                        });
                    } else {
                        Snackbar.make(view, "Preencha Todos Campos!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

            btface.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogview.hide();
                    LoginFacebook();

                }
            });

            dialogview = dialogB.create();
            dialogview.show();


        } else {
            //Lagout da app pelo facebook e email

            getPheferencias();
            Drawable d = new ColorDrawable(Color.BLACK);

            view = View.inflate(AtividadeTabs.this, R.layout.login2, null);
            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            Button button = (Button) view.findViewById(R.id.closefb);
            AlertDialog.Builder dialogB = new AlertDialog.Builder(AtividadeTabs.this).setView(view);
            final AlertDialog dialog = dialogB.create();
            d.setAlpha(0);
            dialog.getWindow().setBackgroundDrawable(d);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dados.Clear();

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("metodo", "sair");
                    map.put("conet", "false");
                    map.put("id", CoreId.getPheferencias(AtividadeTabs.this, "5"));

                    Conexao.Conexao(AtividadeTabs.this, Config.DOMIONIO + "/php/request/login_cadas.php", map, new DadosVolley() {
                        @Override
                        public void geJsonObject(JSONObject jsonObject) {

                            Log.i("LOG", jsonObject + "");

                        }

                        @Override
                        public void ErrorVolley(String messege) {

                        }
                    });

                    profileDrawerItem.withEmail("Email do Usuario").withName("Nome do Usuario").withIcon(CommunityMaterial.Icon.cmd_account);
                    header.updateProfile(profileDrawerItem);
                    estado = false;
                    if (NetDados.getPheferencias(AtividadeTabs.this, "258").equals("true")) {
                        try {
                            loginManager = LoginManager.getInstance();

                            loginManager.logOut();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    drawer.addStickyFooterItem(cadas);
                    dialog.dismiss();
                }
            });


            CircleImageView imageView = (CircleImageView) view.findViewById(R.id.img);
            imageView.setImageDrawable(new IconicsDrawable(AtividadeTabs.this).sizeDp(100).color(Color.LTGRAY).icon(FontAwesome.Icon.faw_user));
            Picasso.with(AtividadeTabs.this).load(urlfato).into(imageView);

            TextView nome2 = (TextView) view.findViewById(R.id.nome);
            nome2.setText(nome);
            TextView email2 = (TextView) view.findViewById(R.id.email);
            email2.setText(email);
            dialog.show();

        }

    }

    public void cadastro() {


        if (estado == false) {

            Intent intent = new Intent(this, AtividadeContainer.class);
            intent.putExtra("fra", 2);
            startActivity(intent);
        } else {
            Snackbar.make(toolbar, " Precisa Sair da sua Conta", Snackbar.LENGTH_SHORT).show();
        }

    }

    public void loading() {
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        imageButton.setVisibility(View.GONE);
        Conexao.Conexao(this, Config.DOMIONIO + "/php/request/getcategoria.php", null, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                Log.i("LOG", jsonObject + "");
                ArrayList<String> strings = new ArrayList<String>();
                strings.add("TODOS OS CURSOS");
                strings.add("CATEGORIAS");
                for (int i = 0; i < jsonObject.length(); i++) {
                    try {
                        JSONObject cate = jsonObject.getJSONObject(i + "");
                        String categoria = cate.getString("nomecate");
                        strings.add(categoria);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (strings.size() > 0) {
                    viewPager.setAdapter(new Tabs(getFragmentManager(), AtividadeTabs.this, strings));
                    tabLayout.setupWithViewPager(viewPager);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void ErrorVolley(String messege) {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                viewPager.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return 0;
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return false;
                    }
                });
            }
        });

    }

    public boolean isServiceRunning(String servicoClassName) {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).service.getClassName().compareTo(servicoClassName) == 0) {
                return true;
            }
        }
        return false;
    }
}
