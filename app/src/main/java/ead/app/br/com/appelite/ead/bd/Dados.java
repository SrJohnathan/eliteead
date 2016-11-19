package ead.app.br.com.appelite.ead.bd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PC on 02/04/2016.
 */
public class Dados {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final String LOGIN_NAME = "LoginFrag";

    public Dados(Context context) {
        preferences = context.getSharedPreferences(LOGIN_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDados( int id,String email, String nome,String telefone, String idface, String urlfoto, boolean estado) {
        editor.clear();
        editor.putInt("2454", id);
        editor.putString("245", email);
        editor.putString("1452", nome);
        editor.putString("1453", idface);
        editor.putString("1485", telefone);
        editor.putString("1454", urlfoto);
        editor.putBoolean("12", estado);

    }

    public void Commit() {
        editor.commit();
    }

    public void Clear() {
        editor.clear();
        editor.putBoolean("12", false);
        editor.commit();
    }


}
