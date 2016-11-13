package ead.elite.app.br.com.appelite.ead;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import ead.elite.app.br.com.appelite.ead.fragments.ConteudoFrag;
import ead.elite.app.br.com.appelite.ead.fragments.MeusCursos;

public class Coneudo extends AppCompatActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout drawer;

    public void setDrawerLayout(DrawerLayout drawerLayout , FrameLayout drawer) {
        this.drawerLayout = drawerLayout;
        this.drawer = drawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteudo);

        //IDS

        toolbar = (Toolbar) findViewById(R.id.toobar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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

        IconicsDrawable namarc = new IconicsDrawable(this);
        namarc.color(Color.WHITE);
        namarc.icon(CommunityMaterial.Icon.cmd_checkbox_multiple_blank_outline);
        namarc.sizeDp(24);


        Fragment frag = null;
        if(savedInstanceState == null) {
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            frag = new ConteudoFrag();
            manager.replace(R.id.viepa, frag, "hh");
            manager.commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void mostrarbt(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void naomostrarbt(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(drawer) && drawerLayout != null){
            drawerLayout.closeDrawer(drawer);
        }else {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title("Confirmação").content("Deseja sair deste curso ?")
                    .positiveText("SIM").negativeText("NÃO")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Coneudo.super.onBackPressed();
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });

            MaterialDialog dialog = builder.build();
            dialog.show();
        }


    }
}