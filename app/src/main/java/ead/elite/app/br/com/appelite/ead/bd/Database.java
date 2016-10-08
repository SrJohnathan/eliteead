package ead.elite.app.br.com.appelite.ead.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ead.elite.app.br.com.appelite.ead.dominio.Mesagem;

/**
 * Created by Pc on 26/06/2016.
 */
public class Database {

    private DBCore dbCore;
    private Context context;
    private SQLiteDatabase database;

    public Database(Context context) {
        this.context = context;
        dbCore = new DBCore(context);
        database = dbCore.getWritableDatabase();

    }

    public void isert(Mesagem mesagem) {

        ContentValues values = new ContentValues();
        values.put("titulo", mesagem.getTitulo());
        values.put("texto", mesagem.getMensagem());
        values.put("leu", mesagem.isLeu());
        values.put("data_env", mesagem.getData());
        values.put("hora", mesagem.getHora());
        database.insert("mensagens", null, values);
        database.close();

    }

    public void leitura(int id,boolean estado) {

        ContentValues values = new ContentValues();
        values.put("leu",estado+"");
        database.update("mensagens", values, "_id =" + id, null);
        database.close();
    }

    public void deleta(int id) {
        Log.i("LOG",id+"");
        database.delete("mensagens","_id = "+id,null);
        database.close();

    }



    public List<Mesagem> buscar() {
        List<Mesagem> list = new ArrayList<>();
        String[] s = new String[]{"_id", "leu", "texto", "titulo", "hora", "data_env"};

        Cursor cursor = database.query("mensagens", s, null, null, null, null, "_id DESC");


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Mesagem mesagem = new Mesagem();

                mesagem.setId(cursor.getInt(0));
                mesagem.setLeu(Boolean.parseBoolean(cursor.getString(1)));
                mesagem.setMensagem(cursor.getString(2));
                mesagem.setTitulo(cursor.getString(3));
                mesagem.setHora(cursor.getString(4));
                mesagem.setData(cursor.getString(5));

                list.add(mesagem);

            } while (cursor.moveToNext());

        }
        database.close();
        return (list);

    }

    public int getLeitura() {
        int nume = 0;
        ArrayList<Boolean> booleen = new ArrayList<>();

        Cursor cursor = database.rawQuery("select leu from mensagens ",null);
        cursor.moveToFirst();
        do{
            if(cursor.getCount() > 0){
                booleen.add(Boolean.parseBoolean(cursor.getString(0)));
            }

        }while (cursor.moveToNext());

        for(int i = 0 ; i < booleen.size();i++){
            if(booleen.get(i) == false){
                nume = nume + 1;
            }
        }
        database.close();

        return nume;

    }
}
