package ead.elite.app.br.com.appelite.ead.dominio;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pc on 18/06/2016.
 */
public class MenuExcluir implements Parcelable {

    private String texto;
    private Drawable icom;

    public MenuExcluir(String texto, Drawable icom) {
        this.texto = texto;
        this.icom = icom;
    }

    protected MenuExcluir(Parcel in) {
        texto = in.readString();
    }

    public static final Creator<MenuExcluir> CREATOR = new Creator<MenuExcluir>() {
        @Override
        public MenuExcluir createFromParcel(Parcel in) {
            return new MenuExcluir(in);
        }

        @Override
        public MenuExcluir[] newArray(int size) {
            return new MenuExcluir[size];
        }
    };

    public String getTexto() {
        return texto;
    }

    public Drawable getIcom() {
        return icom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(texto);
    }
}
