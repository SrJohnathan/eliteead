package ead.elite.app.br.com.appelite.ead;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ead.elite.app.br.com.appelite.ead.bd.Dados;
import ead.elite.app.br.com.appelite.ead.dominio.Capitulos;
import ead.elite.app.br.com.appelite.ead.dominio.Conteudo;
import ead.elite.app.br.com.appelite.ead.fragments.BuscarCate;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class Coneudo extends AppCompatActivity {


    private Capitulos cap;
    int n = 1;
    private WebView webView;
    private FloatingActionButton actionButton;
    private boolean b = true;
    private NestedScrollView scrollView;
    private ProgressBar progressBar;
    private Toolbar toolbar, toolba;
    private ToggleButton check;
    private Drawer drawer;
    private int iduser;

    private ImageButton config;
    private boolean estado;
    private FrameLayout container;
    private Conteudo strtex;
    private MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteudo);
        getPheferencias();

        strtex = getIntent().getParcelableExtra("conteudo");

        if (savedInstanceState != null) {
            cap = (Capitulos) savedInstanceState.getParcelable("conte");
            n = (int) savedInstanceState.getInt("int");
        }


        dialog = new MaterialDialog.Builder(this)
                .title("Carregando")
                .cancelable(false)
                .content("Carregando Conteudo")
                .progress(true, 0).build();
        dialog.show();

        progressBar = (ProgressBar) findViewById(R.id.progress_wheel);
        progressBar.setVisibility(View.VISIBLE);
        check = (ToggleButton) findViewById(R.id.check);


        toolba = (Toolbar) findViewById(R.id.toobar);
        toolbar = (Toolbar) findViewById(R.id.toobarPai);
        webView = (WebView) findViewById(R.id.conteudo);
        scrollView = (NestedScrollView) findViewById(R.id.scroll);

        container = (FrameLayout) findViewById(R.id.container);


        if (cap != null || n != 1) {
            preencher(cap);
            progressBar.setVisibility(View.GONE);
            toolba.setTitle(cap.getCap());
            check.setChecked(cap.isStatus());


        } else {
            getCap("1", "" + iduser, new Get() {
                @Override
                public void getDados(Capitulos capitulos) {

                    if (capitulos != null) {
                        cap = capitulos;
                        webView.loadData("", "text/html", "UTF-8");

                        preencher(capitulos);
                        progressBar.setVisibility(View.GONE);
                        toolba.setTitle(capitulos.getCap());
                        check.setChecked(cap.isStatus());
                    } else {
                        TextView textView = new TextView(Coneudo.this);
                        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
                        textView.setCompoundDrawables(new IconicsDrawable(Coneudo.this).sizeDp(24).color(getResources().getColor(R.color.colorPrimaryDark)), null, null, null);
                        container.addView(textView);
                    }

                }
            });
        }


        drawer = new DrawerBuilder().withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withSliderBackgroundColor(Color.WHITE)
                .withToolbar(toolba)
                .build();

        IconicsDrawable you = new IconicsDrawable(this);
        you.color(Color.RED);
        you.icon(CommunityMaterial.Icon.cmd_youtube_play);
        you.sizeDp(24);


        SectionDrawerItem sectionDrawerItem = new SectionDrawerItem().withDivider(false).withName("Capitulos").withTextColor(Color.GRAY);
        drawer.addItem(sectionDrawerItem);

        for (int i = 1; i <= 10; i++) {
            PrimaryDrawerItem item = new PrimaryDrawerItem().withName("Capitulo " + i).withIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_link).sizeDp(24).color(Color.BLACK));
            drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    dialog.show();
                    if (position == 1) {

                    } else {
                        n = position - 2;

                        getCap((position) + "", "" + iduser, new Get() {
                            @Override
                            public void getDados(Capitulos capitulos) {

                                if (capitulos != null) {

                                    webView.loadData("", "text/html", "UTF-8");

                                    cap = capitulos;
                                    progressBar.setVisibility(View.VISIBLE);
                                    preencher(capitulos);
                                    progressBar.setVisibility(View.GONE);
                                    toolba.setTitle(capitulos.getCap());
                                    check.setChecked(cap.isStatus());
                                }
                            }
                        });

                    }


                    return false;
                }
            });

            drawer.addItem(item);
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(false);


        IconicsDrawable ava = new IconicsDrawable(this);
        ava.color(Color.WHITE);
        ava.icon(CommunityMaterial.Icon.cmd_arrow_right_bold_circle_outline);
        ava.sizeDp(24);


        IconicsDrawable vol = new IconicsDrawable(this);
        vol.color(Color.WHITE);
        vol.icon(CommunityMaterial.Icon.cmd_arrow_left_bold_circle_outline);
        vol.sizeDp(24);

        IconicsDrawable vi = new IconicsDrawable(this);
        vi.color(Color.WHITE);
        vi.icon(CommunityMaterial.Icon.cmd_video);
        vi.sizeDp(24);


        toolbar.inflateMenu(R.menu.menu_con);
        MenuItem avancar = toolbar.getMenu().findItem(R.id.avancar);
        MenuItem voltar = toolbar.getMenu().findItem(R.id.voltar);
        MenuItem video = toolbar.getMenu().findItem(R.id.video);

        video.setIcon(vi);
        avancar.setIcon(ava);
        voltar.setIcon(vol);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dialog.show();

                switch (item.getItemId()) {

                    case R.id.video :
                        dialog.hide();
                      //  JCVideoPlayerStandard.startFullscreen(Coneudo.this, JCVideoPlayerStandard.class, "http://callmenick.com/_development/html5-video/media/demo.mp4", "嫂子辛苦了");
                        Snackbar.make(webView,"Conteúdo indisponível",Snackbar.LENGTH_SHORT).show();

                        break;

                    case R.id.voltar:
                        webView.loadData("", "text/html", "UTF-8");
                        progressBar.setVisibility(View.VISIBLE);

                        if (n == 1) {
                            n = 1;
                            Snackbar.make(toolbar, "Desculpa ", Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                @Override
                                public void onShown(Snackbar snackbar) {
                                    super.onShown(snackbar);
                                }

                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    super.onDismissed(snackbar, event);

                                }
                            }).show();
                            progressBar.setVisibility(View.GONE);
                        } else if (n > 0) {
                            n--;

                            getCap(n + "", "" + iduser, new Get() {
                                @Override
                                public void getDados(Capitulos capitulos) {

                                    if (capitulos != null) {


                                        progressBar.setVisibility(View.VISIBLE);
                                        preencher(capitulos);
                                        progressBar.setVisibility(View.GONE);
                                        toolba.setTitle(capitulos.getCap());
                                        check.setChecked(cap.isStatus());
                                    }

                                }
                            });

                        }
                        break;
                    case R.id.avancar:

                        webView.loadData("", "text/html", "UTF-8");
                        progressBar.setVisibility(View.VISIBLE);
                        if (n == 10) {
                            n = 10;
                            Snackbar.make(scrollView, "Desculpa", Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                @Override
                                public void onShown(Snackbar snackbar) {
                                    super.onShown(snackbar);
                                }

                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    super.onDismissed(snackbar, event);

                                }
                            }).show();
                            progressBar.setVisibility(View.GONE);
                        } else if (n < 11) {
                            n++;

                            getCap(n + "", "" + iduser, new Get() {
                                @Override
                                public void getDados(Capitulos capitulos) {

                                    if (capitulos != null) {
                                        cap = capitulos;


                                        progressBar.setVisibility(View.VISIBLE);
                                        preencher(capitulos);
                                        progressBar.setVisibility(View.GONE);
                                        toolba.setTitle(capitulos.getCap());
                                        check.setChecked(cap.isStatus());
                                    } else {
                                        TextView textView = new TextView(Coneudo.this);
                                        textView.setText("  Verifique sua Conexão com a Internet");
                                        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
                                        textView.setCompoundDrawables(new IconicsDrawable(Coneudo.this).sizeDp(24).color(getResources().getColor(R.color.colorPrimaryDark)).icon(CommunityMaterial.Icon.cmd_alarm), null, null, null);
                                        container.addView(textView);
                                    }

                                }
                            });

                        }
                        break;
                }

                return false;
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cap == null) {
                    Snackbar.make(toolbar, "Carragando...", Snackbar.LENGTH_SHORT).show();
                    check.setChecked(false);
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(Coneudo.this);
                    progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Processando...");
                    progressDialog.show();

                    HashMap<String, String> map = new HashMap<>();
                    map.put("idalu", iduser + "");
                    map.put("idcu", strtex.getId() + "");
                    map.put("metodo", "marcar");
                    map.put("leu", check.isChecked() + "");
                    map.put("idc", cap.getId() + "");

                    Conexao.Conexao(Coneudo.this, Config.DOMIONIO + "/php/request/capitulosapp.php", map, new DadosVolley() {
                        @Override
                        public void geJsonObject(JSONObject jsonObject) {
                            Log.i("LOG", jsonObject + "");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void ErrorVolley(String messege) {

                        }
                    });
                }


            }
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > 0) {
                    if (b == true) {


                        b = false;
                    }


                } else {
                    if (b == false) {


                        b = true;
                    }


                }

            }
        });











      /*  youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(YouTubeStandalonePlayer.createVideoIntent(Coneudo.this,Config.SHA,"5f8cMwYO1XE",0,true,true)));



            }
        });*/


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("conte", cap);
        outState.putInt("int", n);
    }

    @Override
    protected void onStart() {
        super.onStart();


        getPheferencias();
    }

    public void getCap(String numec, String idaluno, final Get get) {

        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "procura");
        map.put("idc", "Capitulo " + numec);
        map.put("idcu", strtex.getId() + "");
        map.put("idalu", idaluno);

        Log.i("LOG", strtex.getId() + "");

        Conexao.Conexao(Coneudo.this, Config.DOMIONIO + "/php/request/capitulosapp.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                Capitulos capitulos = null;
                boolean status;
                Log.i("LOG", jsonObject + "");
                if (jsonObject.has("0")) {
                    try {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));

                            int id = object.getInt("idcapitulos");
                            String nome = object.getString("titulo");
                            String subti = object.getString("subti");
                            String cap = object.getString("capiti_nume");
                            if (object.has("leu")) {

                                status = object.getBoolean("leu");
                            } else {
                                status = false;
                            }

                            String idcur = object.getString("cursos_idcursos");
                            String texto = object.getString("texto");


                            capitulos = new Capitulos(id, nome, status, cap, subti, texto, null);
                            if (scrollView.getScrollY() > 0) {
                                scrollView.smoothScrollTo(0, 0);
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    get.getDados(capitulos);
                }

            }

            @Override
            public void ErrorVolley(String messege) {
                progressBar.setVisibility(View.GONE);
                TextView textView = new TextView(Coneudo.this);
                textView.setText("  Verifique sua Conexão com a Internet");
                textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
                textView.setCompoundDrawables(new IconicsDrawable(Coneudo.this).sizeDp(24).color(getResources().getColor(R.color.colorPrimaryDark)).icon(CommunityMaterial.Icon.cmd_alarm), null, null, null);
                container.addView(textView);
            }
        });


    }

    public void preencher(Capitulos capitulos) {
        String webContent = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\"></head>"
                + "<body>" + capitulos.getTexto1() + "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script> <script   src=\"https://code.jquery.com/jquery-2.2.3.js\"   integrity=\"sha256-laXWtGydpwqJ8JA+X9x2miwmaiKhn8tVmOVEigRNtP4=\"   crossorigin=\"anonymous\"></script>" +
                "<script>$(document).ready(function(){\n" +
                "            $(\"img\").addClass(\"img-responsive\");\n" +
                "    });\n</script></body></html>";

        webView.loadData(webContent, "text/html", "UTF-8");

        dialog.dismiss();

    }

    public void getPheferencias() {
        SharedPreferences preferences = getSharedPreferences(Dados.LOGIN_NAME, Context.MODE_PRIVATE);
        iduser = preferences.getInt("2454", iduser);
        estado = preferences.getBoolean("12", estado);
    }

    @Override
    public void onBackPressed() {

        if (JCVideoPlayer.backPress()) {
            return;
        }
        if (drawer.isDrawerOpen() == true) {
            drawer.closeDrawer();

        } else {

            if (!dialog.isShowing()) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title("Confirmação").content("Deseja Sair ?")
                        .positiveText("SIM").negativeText("NÃO")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                fiinish();
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });

                MaterialDialog dialog = builder.build();
                dialog.show();
            } else {

            }


        }

    }

    interface Get {
        public void getDados(Capitulos capitulos);
    }

    public void fiinish() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }


}
