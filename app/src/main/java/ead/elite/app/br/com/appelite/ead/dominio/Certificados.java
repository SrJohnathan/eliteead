package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 07/03/2016.
 */
public class Certificados implements Parcelable {
    private int id;
    private String nome;
    private double nota;
    private String hotas;
    private String datap;
    private String dataf;
    private boolean baixou;

    public Certificados(int id, String nome, double nota, String hotas, String datap, String dataf, boolean baixou) {
        this.id = id;
        this.nome = nome;
        this.nota = nota;
        this.hotas = hotas;
        this.datap = datap;
        this.dataf = dataf;
        this.baixou = baixou;
    }

    protected Certificados(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        nota = in.readDouble();
        hotas = in.readString();
        datap = in.readString();
        dataf = in.readString();
        baixou = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeDouble(nota);
        dest.writeString(hotas);
        dest.writeString(datap);
        dest.writeString(dataf);
        dest.writeByte((byte) (baixou ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Certificados> CREATOR = new Creator<Certificados>() {
        @Override
        public Certificados createFromParcel(Parcel in) {
            return new Certificados(in);
        }

        @Override
        public Certificados[] newArray(int size) {
            return new Certificados[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getNota() {
        return nota;
    }

    public String getHotas() {
        return hotas;
    }

    public String getDatap() {
        return datap;
    }

    public String getDataf() {
        return dataf;
    }

    public boolean isBaixou() {
        return baixou;
    }
}