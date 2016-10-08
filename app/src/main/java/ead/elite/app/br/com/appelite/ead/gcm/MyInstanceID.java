package ead.elite.app.br.com.appelite.ead.gcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import ead.elite.app.br.com.appelite.ead.bd.CoreId;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;

/**
 * Created by PC on 16/04/2016.
 */
public class MyInstanceID extends FirebaseInstanceIdService {
    CoreId dados;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("LOG", "GCM Registration Token: " + refreshedToken);

     /*   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("status",false).apply();
        Intent intent = new Intent(this,RegisterIntenteGcm.class);
        startService(intent); */

        getNetworkToken(refreshedToken);


    }




    private void getNetworkToken(String token){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean status = preferences.getBoolean("status",false) ;

        if(!status ){
            dados = new CoreId(getApplicationContext());
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("token",token);
            hashMap.put("metodo","insert");
            Log.i("LOG",token);

            Conexao.Conexao(this, Config.DOMIONIO+"/php/request/token.php", hashMap, new DadosVolley() {
                @Override
                public void geJsonObject(JSONObject jsonObject) {


                    try {
                        JSONObject object = jsonObject.getJSONObject("0");

                        dados.setDados("5",object.getString("iddevice"));
                        dados.Commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void ErrorVolley(String messege) {

                }
            });


            preferences.edit().putBoolean("status",token !=null && token.trim().length() > 0).apply();

        }


    }



}
