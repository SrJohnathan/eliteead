package ead.elite.app.br.com.appelite.ead.gcm;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.bd.Database;
import ead.elite.app.br.com.appelite.ead.dominio.Mesagem;

/**
 * Created by PC on 16/04/2016.
 */
public class GcmLister extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mapa = remoteMessage.getData();
        String json = mapa.get("notification");
        Gson gson = new Gson();
        Mesagem mesagem = gson.fromJson(json, Mesagem.class);


        Database database = new Database(getApplicationContext());

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle(mesagem.getTitulo())
                        .setSound(defaultSoundUri)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(largeIcon)
                        .setContentText(mesagem.getMensagem());

        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(55, mBuilder.build());

        database.isert(mesagem);


    }


}
