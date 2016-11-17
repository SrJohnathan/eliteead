package ead.elite.app.br.com.appelite.ead.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;

import ead.elite.app.br.com.appelite.ead.dominio.ProgressText;
import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.elite.app.br.com.appelite.ead.net.Config;
import ead.elite.app.br.com.appelite.ead.net.volley.Conexao;

/**
 * Created by Pc on 30/09/2016.
 */

public class ServiceFoto extends Service {


    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle bundle = intent.getExtras();
        Log.i("LOG",String.valueOf(bundle.getInt("user")));
        HashMap<String, String> map = new HashMap<>();
        map.put("metodo", "update_foto");
        map.put("id", bundle.getInt("user") + "");
        map.put("foto", bundle.getString("img"));
        Conexao.Conexao(getApplicationContext(), Config.DOMIONIO + "/php/request/perfil.php", map, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                stopService(new Intent("SERVICO_FOTO"));
                ProgressText text = new ProgressText("servicofoot","go");
                EventBus.getDefault().post(text);
            }

            @Override
            public void ErrorVolley(String messege) {


            }
        });


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProgressText progressText){

    }
}
