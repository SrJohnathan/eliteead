package ead.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import ead.app.br.com.appelite.ead.R;
import ead.app.br.com.appelite.ead.dominio.Acerto;
import ead.app.br.com.appelite.ead.dominio.PosicaoRes;
import ead.app.br.com.appelite.ead.dominio.Prova;
import ead.app.br.com.appelite.ead.interfaces.OnClickItemProva;


/**
 * Created by PC on 18/03/2016.
 */
public class AdapterProvas extends RecyclerView.Adapter<AdapterProvas.MyviewPro> {

    private LayoutInflater inflater;
    private Context context;
    private View view;
    private List<Prova> provas;
    private OnClickItemProva itemProva;
    private Acerto acerto;
    private ArrayList<Integer> it;
    private boolean correcao = false;
    private List<PosicaoRes> res;

    MyviewPro myviewPro;


    public AdapterProvas(final Context context, List<Prova> list) {
        this.context = context;
        provas = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void setAcerto(Acerto acerto, List<PosicaoRes> res) {
        this.acerto = acerto;
        it = new ArrayList<>();
        for (int i = 0; i < acerto.getQuestao().size(); i++) {
            it.add(acerto.getQuestao().get(i).getQues());
        }

        this.res = res;
        correcao = true;
        notifyDataSetChanged();
    }


    public void setItemProva(OnClickItemProva itemProva) {
        this.itemProva = itemProva;
    }

    @Override
    public MyviewPro onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.pergunta, parent, false);
        myviewPro = new MyviewPro(view);
        return myviewPro;
    }

    @Override
    public void onBindViewHolder(MyviewPro holder, int position) {


        //  holder.questao.setText("Questão "+ (position+1));
        holder.questao.setText(provas.get(position).getQuestao());
        holder.pergunta.setText(provas.get(position).getPergunta());
        holder.res1.setText(provas.get(position).getResA());
        holder.res2.setText(provas.get(position).getResB());
        holder.res3.setText(provas.get(position).getResC());
        holder.res4.setText(provas.get(position).getResD());
        holder.fita.setVisibility(View.GONE);


        if (correcao == true) {
            if (it.contains(provas.get(position).getId())) {
                holder.fita.setVisibility(View.VISIBLE);
                holder.fita.setImageDrawable(new IconicsDrawable(context).icon(FontAwesome.Icon.faw_thumbs_up).sizeDp(24)
                        .color(context.getResources().getColor(R.color.md_green_600)));
                switch (res.get(position).getResposta()) {
                    case 1:
                        holder.res1.setChecked(true);
                        holder.res1.setBackgroundResource(R.drawable.certo);

                        break;
                    case 2:
                        holder.res2.setChecked(true);
                        holder.res2.setBackgroundResource(R.drawable.certo);

                        break;
                    case 3:
                        holder.res3.setChecked(true);
                        holder.res3.setBackgroundResource(R.drawable.certo);

                        break;
                    case 4:
                        holder.res4.setChecked(true);
                        holder.res4.setBackgroundResource(R.drawable.certo);

                        break;
                    default:
                        break;


                }
            } else {
                holder.fita.setVisibility(View.VISIBLE);
                holder.fita.setImageDrawable(new IconicsDrawable(context).icon(FontAwesome.Icon.faw_thumbs_down).sizeDp(24)
                        .color(context.getResources().getColor(R.color.md_red_600)));
                switch (res.get(position).getResposta()) {
                    case 1:
                        holder.res1.setChecked(true);
                        holder.res1.setBackgroundResource(R.drawable.errado);
                        break;
                    case 2:
                        holder.res2.setChecked(true);
                        holder.res2.setBackgroundResource(R.drawable.errado);

                        break;
                    case 3:
                        holder.res3.setChecked(true);
                        holder.res3.setBackgroundResource(R.drawable.errado);

                        break;
                    case 4:
                        holder.res4.setChecked(true);
                        holder.res4.setBackgroundResource(R.drawable.errado);

                        break;
                    default:
                        break;


                }

            }


        } else {

            switch (provas.get(position).getAcerto()) {
                case 1:
                    holder.res1.setChecked(true);
                    break;
                case 2:
                    holder.res2.setChecked(true);
                    break;
                case 3:
                    holder.res3.setChecked(true);
                    break;
                case 4:
                    holder.res4.setChecked(true);
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return provas.size();
    }

    public void SavidInstance() {

    }

    class MyviewPro extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int response = 0;
        private RadioGroup radioGroup;
        private TextView questao, pergunta;
        private RadioButton res1, res2, res3, res4;
        private ImageView fita;


        public MyviewPro(View itemView) {
            super(itemView);


            radioGroup = (RadioGroup) itemView.findViewById(R.id.group);
            questao = (TextView) itemView.findViewById(R.id.questao);
            pergunta = (TextView) itemView.findViewById(R.id.pergunta);
            res1 = (RadioButton) itemView.findViewById(R.id.res1);
            res2 = (RadioButton) itemView.findViewById(R.id.res2);
            res3 = (RadioButton) itemView.findViewById(R.id.res3);
            res4 = (RadioButton) itemView.findViewById(R.id.res4);
            fita = (ImageView) itemView.findViewById(R.id.pontos);

            res1.setOnClickListener(this);
            res2.setOnClickListener(this);
            res3.setOnClickListener(this);
            res4.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {


            if (itemProva != null) {

                switch (v.getId()) {
                    case R.id.res1:
                        response = 1;


                        break;
                    case R.id.res2:
                        response = 2;


                        break;
                    case R.id.res3:
                        response = 3;


                        break;
                    case R.id.res4:
                        response = 4;


                        break;
                    default:
                        response = 0;

                        break;


                }

                itemProva.onClick(v, provas.get(getAdapterPosition()).getId(), response, getAdapterPosition());


            }
        }


    }
}
