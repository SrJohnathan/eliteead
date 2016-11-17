package ead.elite.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.componets.CustonTextView;
import ead.elite.app.br.com.appelite.ead.componets.libs.CustonRadioGroup;
import ead.elite.app.br.com.appelite.ead.dominio.Capitulos;


/**
 * Created by JOHNATHAN on 14/01/2016.
 */
public class AdapterConteudo extends RecyclerView.Adapter<AdapterConteudo.MyView> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Capitulos> list;
    private AdapterConteudo.OpemDrawer opemDrawer;
    private CustonRadioGroup group;


    public AdapterConteudo(Context mContext, List<Capitulos> list) {
        this.list = list;
        this.mContext = mContext;
        group = new CustonRadioGroup();

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.tabcuston, parent, false);


        return new MyView(view);
    }

    public void atualizarList(List<Capitulos> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {

        holder.capitulo.setText(list.get(position).getCap());
        holder.titulo.setText(list.get(position).getNome());
        group.add(holder.radioButton,position);
        if (list.get(position).isStatus()) {
            holder.marcar.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.analizado));
            holder.marcar.setText("ANALIZADO");
            holder.marcar.setTextColor(mContext.getResources().getColor(R.color.md_green_600));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOpemDrawer(OpemDrawer opemDrawer) {
        this.opemDrawer = opemDrawer;
    }

    class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CustonTextView capitulo, titulo;
        private TextView marcar;
        private RadioButton radioButton;

        public MyView(View itemView) {
            super(itemView);

            capitulo = (CustonTextView) itemView.findViewById(R.id.titulo);
            titulo = (CustonTextView) itemView.findViewById(R.id.subtitulo);
            marcar = (TextView) itemView.findViewById(R.id.macar);
            radioButton = (RadioButton) itemView.findViewById(R.id.focus);

            radioButton.setClickable(false);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
                group.commitCick(getAdapterPosition());
            if (opemDrawer != null) {
                opemDrawer.Click(view, getAdapterPosition());
            }

        }
    }

    public interface OpemDrawer {
        public void Click(View v, int position);
    }
}
