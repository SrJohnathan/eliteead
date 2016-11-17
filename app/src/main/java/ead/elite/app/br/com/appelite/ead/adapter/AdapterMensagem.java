package ead.elite.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.List;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.dominio.Mesagem;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickFrag;


/**
 * Created by Pc on 18/06/2016.
 */
public class AdapterMensagem extends RecyclerView.Adapter<AdapterMensagem.Myview> {

    private Context context;
    private LayoutInflater layoutInflater;
    private OnClickMensage excluir;
    private List<Mesagem> list;
    private OnClickFrag onClickFrag;



    public AdapterMensagem(Context context , List<Mesagem> list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.aviso,parent,false);

        Myview myview = new Myview(view);

        return myview;
    }

    @Override
    public void onBindViewHolder(Myview holder, int position) {


        holder.titulo.setText(list.get(position).getTitulo());
        holder.hora.setText(list.get(position).getHora());
        holder.data.setText(list.get(position).getData());

        if(list.get(position).isLeu()){
            Log.i("LOG",list.get(position).isLeu()+"");
            holder.box.setImageDrawable(new IconicsDrawable(context).sizeDp(30).color(context.getResources().getColor(R.color.md_blue_grey_600)).icon(FontAwesome.Icon.faw_envelope_o));

        }else{
            Log.i("LOG",list.get(position).isLeu()+"");
            holder.box.setImageDrawable(new IconicsDrawable(context).sizeDp(30).color(context.getResources().getColor(R.color.md_blue_grey_600)).icon(FontAwesome.Icon.faw_envelope));

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myview extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView box, exclui;
        private TextView titulo , data , hora;
        private RelativeLayout relativeLayout;

        public Myview(View itemView) {
            super(itemView);


            box = (ImageView) itemView.findViewById(R.id.box);
            exclui = (ImageView) itemView.findViewById(R.id.exclu);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            data = (TextView) itemView.findViewById(R.id.periudo);
            hora = (TextView) itemView.findViewById(R.id.hora);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item);

            relativeLayout.setOnClickListener(this);
            exclui.setOnClickListener(this);
            exclui.setImageDrawable(new IconicsDrawable(context).icon(FontAwesome.Icon.faw_trash_o).color(Color.RED).sizeDp(24));
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.exclu :
                    if(excluir != null){
                        excluir.Click(v,getAdapterPosition());
                    }
                    break;
                case  R.id.item :
                    if(onClickFrag != null){
                        onClickFrag.Click(v,getAdapterPosition());
                    }
                    break;
            }


        }
    }

    public void atualizar(List<Mesagem> list){
        this.list = list;
        notifyDataSetChanged();

    }

    public void setExcluirClickMensage(OnClickMensage onClickMensage){
        excluir = onClickMensage;

    }

    public void setOnClickFrag (OnClickFrag onClickFrag){
        this.onClickFrag = onClickFrag;
    }

    public interface OnClickMensage{
        public void Click(View view ,int position);
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }

    public void addItem(Mesagem notiMensagem , int position){
        list.add(notiMensagem);
        notifyItemInserted(position);
        notifyDataSetChanged();

    }
}
