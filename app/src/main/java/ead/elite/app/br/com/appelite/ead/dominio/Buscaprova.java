package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pc on 15/06/2016.
 */
public class Buscaprova implements Parcelable {

    private int iduser;
    private int idcurso;
    private String prova;


    public Buscaprova(int iduser, int idcurso, String prova) {
        this.iduser = iduser;
        this.idcurso = idcurso;
        this.prova = prova;
    }

    protected Buscaprova(Parcel in) {
        iduser = in.readInt();
        idcurso = in.readInt();
        prova = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iduser);
        dest.writeInt(idcurso);
        dest.writeString(prova);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Buscaprova> CREATOR = new Creator<Buscaprova>() {
        @Override
        public Buscaprova createFromParcel(Parcel in) {
            return new Buscaprova(in);
        }

        @Override
        public Buscaprova[] newArray(int size) {
            return new Buscaprova[size];
        }
    };

    public int getIduser() {
        return iduser;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public String getProva() {
        return prova;
    }
}
