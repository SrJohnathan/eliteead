package ead.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import ead.app.br.com.appelite.ead.R;
import ead.app.br.com.appelite.ead.componets.TriangleImageView;
import ead.app.br.com.appelite.ead.dominio.Certificados;
import ead.app.br.com.appelite.ead.net.Config;


/**
 * Created by PC on 07/03/2016.
 */
public class AdapterCertificado extends RecyclerView.Adapter<AdapterCertificado.Myview> {

    private LayoutInflater layoutInflater;
    private List<Certificados> list;
    private Context context;
    private GetClick click;


    public AdapterCertificado(Context context, List list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.certificado,parent,false);
        Myview myview = new Myview(view);
        return myview;
    }

    public void setClick(GetClick click){
        this.click = click;
    }

    @Override
    public void onBindViewHolder(Myview holder, int position) {

        holder.hora.setText(list.get(position).getHotas()+"hrs");
        holder.nota.setText("Média:"+list.get(position).getNota());
        holder.tempo.setText(list.get(position).getDatap()+" até "+list.get(position).getDataf());


        String youFilePath = Environment.getExternalStorageDirectory().toString() + "/EliteEad";
        File file = new File(youFilePath,  list.get(position).getNome() + ".pdf");
        if(file.exists() == true){

            holder.donwload.setImageDrawable(new IconicsDrawable(context).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_eye).sizeDp(30));
            holder.donwload.setBackgroundResource(R.drawable.btverde);

        }else if(file.exists() == false) {

          holder.donwload.setImageDrawable(new IconicsDrawable(context).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_download).sizeDp(30));

        }


        holder.titulo.setText(list.get(position).getNome());
        Picasso.with(context).load(Config.DOMIONIO+"/php/server/image.php?metodo=cursos&id="+list.get(position).getId()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myview extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton donwload;
        private TriangleImageView imageView;
        private TextView titulo,nota,hora,tempo;

        public Myview(View itemView) {
            super(itemView);

            donwload = (ImageButton) itemView.findViewById(R.id.donwload);
            imageView = (TriangleImageView) itemView.findViewById(R.id.img);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            nota = (TextView) itemView.findViewById(R.id.nota1);
            hora = (TextView) itemView.findViewById(R.id.nota2);
            tempo = (TextView) itemView.findViewById(R.id.periudo);

            donwload.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            if(click != null){
                click.Click(v,getAdapterPosition());
            }
        }
    }

    public interface GetClick{
        public void Click(View view,int positon);
    }
}
