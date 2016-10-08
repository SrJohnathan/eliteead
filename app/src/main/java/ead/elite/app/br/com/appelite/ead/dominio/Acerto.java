package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Pc on 19/06/2016.
 */
public class Acerto implements Parcelable{
    private int pontos;
    private List<Estado> questao;



    public Acerto() {
    }

    public Acerto(int pontos, List<Estado> questao) {
        this.pontos = pontos;
        this.questao = questao;
    }

    protected Acerto(Parcel in) {
        pontos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pontos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Acerto> CREATOR = new Creator<Acerto>() {
        @Override
        public Acerto createFromParcel(Parcel in) {
            return new Acerto(in);
        }

        @Override
        public Acerto[] newArray(int size) {
            return new Acerto[size];
        }
    };

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public List<Estado> getQuestao() {
        return questao;
    }

    public void setQuestao(List<Estado> questao) {
        this.questao = questao;
    }

    public static class Estado {

        private int ques;
        private boolean boo;

        public Estado() {
        }

        public Estado(int ques, boolean boo) {
            this.ques = ques;
            this.boo = boo;
        }

        public int getQues() {
            return ques;
        }

        public void setQues(int ques) {
            this.ques = ques;
        }

        public boolean isBoo() {
            return boo;
        }

        public void setBoo(boolean boo) {
            this.boo = boo;
        }
    }

}
