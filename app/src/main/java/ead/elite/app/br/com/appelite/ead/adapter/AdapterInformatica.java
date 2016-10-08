package ead.elite.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.dominio.Curso;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickFrag;
import ead.elite.app.br.com.appelite.ead.net.Config;

/**
 * Created by PC on 07/03/2016.
 */
public class AdapterInformatica extends RecyclerView.Adapter<AdapterInformatica.Myview> {


    private OnClickFrag onClickFrag;
    private LayoutInflater layoutInflater;
    private List<Curso> list;
    private Context context;
    private double densidade;
    private int largura;
    private  int altura,pixell;


    public AdapterInformatica(Context context, List list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        densidade = context.getResources().getDisplayMetrics().density;
        largura = context.getResources().getDisplayMetrics().widthPixels - (int) (14 * densidade + 0.5f);
        altura = (largura / 16) * 9;
        pixell = (int) (6 * densidade + 0.5f);

    }





    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.curso,parent,false);
        Myview myview = new Myview(view);
        return myview;
    }

    public void setOnClickFrag(OnClickFrag onClickFrag){
        this.onClickFrag = onClickFrag;
    }

    public void atualizar( List<Curso> cursos){
        list = cursos;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final Myview holder, int position) {




        holder.titulo.setText(list.get(position).getNome().toUpperCase());
        holder.categoria.setText(list.get(position).getCategoria());
        if(list.get(position).isPago() == false){
            holder.preco.setText("Gratuito");
            holder.preco.setTextColor(context.getResources().getColor(R.color.md_green_600));
        }else {
            holder.preco.setText("R$"+list.get(position).getPreco());
        }

        holder.horas.setText(list.get(position).getHoras()+"Hrs");


        ControllerListener listener = new BaseControllerListener(){};

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Config.DOMIONIO+"/php/server/image.php?metodo=cursos&id="+list.get(position).getId())
                .setControllerListener(listener)
                .setOldController(holder.imageView.getController())
                .build();
        holder.imageView.setController(controller);
        RoundingParams params = RoundingParams.fromCornersRadii(pixell,pixell,0,0);
        holder.imageView.getHierarchy().setRoundingParams(params);



        try {
            YoYo.with(Techniques.Shake).duration(700).playOn(holder.linearLayout);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class Myview extends RecyclerView.ViewHolder implements View.OnClickListener{
        RatingBar ratingBar;
        private CardView linearLayout;
        TextView titulo,categoria,preco,horas;
        SimpleDraweeView imageView;
        private ProgressBar progressBar;

        public Myview(View itemView) {
            super(itemView);


            //IDS
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            categoria = (TextView) itemView.findViewById(R.id.cate);
            preco = (TextView) itemView.findViewById(R.id.preco);
            linearLayout = (CardView) itemView.findViewById(R.id.animacao);
            horas = (TextView) itemView.findViewById(R.id.tempo);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.menuimg);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progss);

            itemView.setOnClickListener(this);

           /* ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

            LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();

            layerDrawable.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.md_blue_200), PorterDuff.Mode.SRC_ATOP);
            layerDrawable.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.md_blue_200), PorterDuff.Mode.SRC_ATOP);
            layerDrawable.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.md_blue_600), PorterDuff.Mode.SRC_ATOP);
            */
        }

        @Override
        public void onClick(View v) {
            if(onClickFrag != null){
                onClickFrag.Click(v,getAdapterPosition());
            }
        }
    }
}
