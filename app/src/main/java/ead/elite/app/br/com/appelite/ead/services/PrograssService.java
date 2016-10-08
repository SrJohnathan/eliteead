package ead.elite.app.br.com.appelite.ead.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ead.elite.app.br.com.appelite.ead.Prova;
import ead.elite.app.br.com.appelite.ead.componets.ContagemNotification;
import ead.elite.app.br.com.appelite.ead.dominio.ProgressText;

/**
 * Created by Pc on 26/09/2016.
 */

public class PrograssService extends Service {

    ContagemNotification notification;


    @Override
    public void onCreate() {
        super.onCreate();

         notification = ContagemNotification.Instance(getApplicationContext());
        EventBus.getDefault().register(this);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            if(!notification.isRodando()){
                notification.start();

                notification.setTextView(new ContagemNotification.OnTextView() {
                    @Override
                    public void Update(String progress) {
                        ProgressText progressText = new ProgressText(PrograssService.this+"",progress);
                        EventBus.getDefault().post(progressText);
                    }
                });

                notification.setFinalize(new ContagemNotification.OnFinalize() {
                    @Override
                    public void finish() {

                        ProgressText progressText = new ProgressText(PrograssService.this+"","acabou");
                        EventBus.getDefault().post(progressText);
                    }
                });

            }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notification.cencela();
        EventBus.getDefault().unregister(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProgressText progressText){

    }
}
