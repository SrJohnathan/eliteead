package ead.elite.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.squareup.picasso.Picasso;

import java.util.List;

import ead.elite.app.br.com.appelite.ead.Coneudo;
import ead.elite.app.br.com.appelite.ead.Prova;
import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.componets.ContagemNotification;
import ead.elite.app.br.com.appelite.ead.componets.MyTextViewArial;
import ead.elite.app.br.com.appelite.ead.dominio.Conteudo;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickFrag;
import ead.elite.app.br.com.appelite.ead.net.Config;

/**
 * Created by PC on 08/03/2016.
 */
public class AdapterMeusCursos extends RecyclerSwipeAdapter<AdapterMeusCursos.MyHolder> {
    private List<Conteudo> list;
    private Context context;
    private LayoutInflater inflater;
    private OnClickFrag onClickFrag;


    public AdapterMeusCursos(Context context, List list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.meus_cursos, parent, false);

        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder viewHolder, int position) {
        viewHolder.progress.setProgress(list.get(position).getPorce() * 10);
        viewHolder.titulo.setText(list.get(position).getNome());
        Picasso.with(context).load(Config.DOMIONIO+"/php/server/image.php?metodo=cursos&id=" + list.get(position).getId()).resize(70,50).into(viewHolder.img);

    }


    public void setOnClickFrag(OnClickFrag onClickFrag) {
        this.onClickFrag = onClickFrag;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button conteudo, btprova,notas;
        private ImageView puxa, img;
        private SwipeLayout swipeLayout;
        private CircleProgress progress;
        private TextView titulo;

        public MyHolder(View itemView) {
            super(itemView);

            //IDS
            conteudo = (Button) itemView.findViewById(R.id.conteudo);
            notas = (Button) itemView.findViewById(R.id.notas);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            btprova = (Button) itemView.findViewById(R.id.btprova);
            progress = (CircleProgress) itemView.findViewById(R.id.donut_progress);
            puxa = (ImageView) itemView.findViewById(R.id.puxa);
            img = (ImageView) itemView.findViewById(R.id.img);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

            puxa.setImageDrawable(new IconicsDrawable(context).sizeDp(40).icon(CommunityMaterial.Icon.cmd_play_circle_outline).color(Color.GRAY));
            puxa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    swipeLayout.open();
                }
            });


            btprova.setOnClickListener(this);
            conteudo.setOnClickListener(this);
            notas.setOnClickListener(this);
           // notas.setImageDrawable(new IconicsDrawable(context).color(Color.WHITE).sizeDp(30).icon(CommunityMaterial.Icon.cmd_numeric));
          //  conteudo.setImageDrawable(new IconicsDrawable(context).color(Color.WHITE).sizeDp(30).icon(CommunityMaterial.Icon.cmd_clipboard_text));
           // btprova.setImageDrawable(new IconicsDrawable(context).color(Color.WHITE).sizeDp(30).icon(FontAwesome.Icon.faw_question_circle));

        }

        @Override
        public void onClick(View v) {
            if (onClickFrag != null) {
                onClickFrag.Click(v, getAdapterPosition());
            }
        }
    }
}
