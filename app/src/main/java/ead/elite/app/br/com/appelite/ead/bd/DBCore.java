package ead.elite.app.br.com.appelite.ead.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pc on 26/06/2016.
 */
public class DBCore extends SQLiteOpenHelper {

    private static final String NOME = "ead";
    private static  final  int VERSAO = 7;

    public DBCore(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE mensagens (  _id INTEGER primary key autoincrement ,data_env VARCHAR(12) NULL, titulo VARCHAR(45) NULL, texto TEXT NULL, hora VARCHAR(8) NULL, leu VARCHAR(6) NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE mensagens;");
                onCreate(db);
    }
}
