package ead.elite.app.br.com.appelite.ead.bd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pc on 12/05/2016.
 */
public class NetDados {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final String COUT = "Cout";

    public NetDados(Context context) {
        preferences = context.getSharedPreferences(COUT, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDados( String tag,String valor) {

        editor.putString(tag, valor);


    }

    public static String getPheferencias(Context context,String tag) {
        SharedPreferences preferences = context.getSharedPreferences(COUT, Context.MODE_PRIVATE);
        return preferences.getString(tag, "");
    }

    public void Commit() {
        editor.commit();
    }

    public void Remove(String id) {
        editor.remove(id);
        editor.commit();
    }

    public void Clear() {
        editor.clear();
        editor.putBoolean("12", false);
        editor.commit();
    }


}
