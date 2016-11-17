package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 19/03/2016.
 */
public class PosicaoRes implements Parcelable {

    private int id;
    private int resposta;
    private  int position;

    public PosicaoRes(int id, int resposta, int position) {
        this.id = id;
        this.resposta = resposta;
        this.position = position;
    }

    protected PosicaoRes(Parcel in) {
        id = in.readInt();
        resposta = in.readInt();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(resposta);
        dest.writeInt(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PosicaoRes> CREATOR = new Creator<PosicaoRes>() {
        @Override
        public PosicaoRes createFromParcel(Parcel in) {
            return new PosicaoRes(in);
        }

        @Override
        public PosicaoRes[] newArray(int size) {
            return new PosicaoRes[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getResposta() {
        return resposta;
    }

    public int getPosition() {
        return position;
    }
}
