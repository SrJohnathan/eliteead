package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 20/03/2016.
 */
public class Conteudo implements Parcelable {

    String nome;
    int id;
    int porce;
    int idAluno;

    public Conteudo(String nome, int id, int porce, int idAluno) {
        this.nome = nome;
        this.id = id;
        this.porce = porce;
        this.idAluno = idAluno;
    }

    protected Conteudo(Parcel in) {
        nome = in.readString();
        id = in.readInt();
        porce = in.readInt();
        idAluno = in.readInt();
    }

    public static final Creator<Conteudo> CREATOR = new Creator<Conteudo>() {
        @Override
        public Conteudo createFromParcel(Parcel in) {
            return new Conteudo(in);
        }

        @Override
        public Conteudo[] newArray(int size) {
            return new Conteudo[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPorce() {
        return porce;
    }

    public void setPorce(int porce) {
        this.porce = porce;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeInt(id);
        dest.writeInt(porce);
        dest.writeInt(idAluno);
    }
}
