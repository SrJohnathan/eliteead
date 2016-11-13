package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 07/03/2016.
 */
public class Capitulos implements Parcelable {

    private int id;
    private String nome;
    private boolean status;
    private String cap;
    private String subtitulo1;
    private String texto1;
    private String video;

    public Capitulos(int id, String nome, boolean status, String cap, String subtitulo1, String texto1, String video) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.cap = cap;
        this.subtitulo1 = subtitulo1;
        this.texto1 = texto1;
        this.video = video;
    }

    public Capitulos() {
    }

    protected Capitulos(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        status = in.readByte() != 0;
        cap = in.readString();
        subtitulo1 = in.readString();
        texto1 = in.readString();
        video = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(cap);
        dest.writeString(subtitulo1);
        dest.writeString(texto1);
        dest.writeString(video);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Capitulos> CREATOR = new Creator<Capitulos>() {
        @Override
        public Capitulos createFromParcel(Parcel in) {
            return new Capitulos(in);
        }

        @Override
        public Capitulos[] newArray(int size) {
            return new Capitulos[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public void setSubtitulo1(String subtitulo1) {
        this.subtitulo1 = subtitulo1;
    }

    public void setTexto1(String texto1) {
        this.texto1 = texto1;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isStatus() {
        return status;
    }

    public String getCap() {
        return cap;
    }

    public String getSubtitulo1() {
        return subtitulo1;
    }

    public String getTexto1() {
        return texto1;
    }

    public String getVideo() {
        return video;
    }
}
