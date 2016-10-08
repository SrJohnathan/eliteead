package ead.elite.app.br.com.appelite.ead.componets;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.Calendar;

import ead.elite.app.br.com.appelite.ead.Prova;
import ead.elite.app.br.com.appelite.ead.R;

/**
 * Created by PC on 10/03/2016.
 */
public class ContagemNotification extends CountDownTimer implements Runnable {

    NotificationCompat.Builder builder;
    private Context context;
    RemoteViews remoteViews;
    private Thread thread;
    TextView textView;
    NotificationManager manager;
    boolean t = false;
    static boolean rodando = false;
    public long times;
    private ContagemNotification.OnFinalize finalize;
    private ContagemNotification.OnTextView onTextView;


    public static ContagemNotification Instance(Context context1){
        ContagemNotification notification = new ContagemNotification(context1, 30 * 60 * 1000, 1000);


        return notification;
    }

    public ContagemNotification(Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.context = context;

        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logo);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        //  builder.setLargeIcon(bitmap);
        builder.setContentTitle("Prova");
        builder.setOngoing(true);

        proges();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    }


    public  boolean isRodando(){
        return rodando;
    }

    @Override
    public void onTick(final long millisUntilFinished) {
        times = millisUntilFinished;
        rodando = true;
        builder.setContentText(getCorrer(true, millisUntilFinished) + ":" + getCorrer(false, millisUntilFinished));
        manager.notify(2414, builder.build());

        onTextView.Update(getCorrer(true, millisUntilFinished) + ":" + getCorrer(false, millisUntilFinished));

    }

    public void cencela() {
        cancel();
        manager.cancel(2414);
        t = true;
        rodando = false;

    }


    @Override
    public void onFinish() {
        times -= 1000;
        builder.setContentText(getCorrer(true, times) + ":" + getCorrer(false, times));
        manager.cancel(2414);
        rodando = false;

        if(finalize != null){
            finalize.finish();
        }


    }

    public void setFinalize(OnFinalize finalize){
        this.finalize =  finalize;
    }

    public void setTextView(OnTextView textView) {
        this.onTextView = textView;
    }

    public String getCorrer(boolean isminute, long millisUntilFinished) {

        String s;

        int contcalen = isminute ? Calendar.MINUTE : Calendar.SECOND;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);

        s = calendar.get(contcalen) < 10 ? "0" + calendar.get(contcalen) : "" + calendar.get(contcalen);

        return (s);
    }


    public void proges() {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int d = 0;
                while (t != true) {
                    d = d + 1;
                    SystemClock.sleep(1000);

                    builder.setProgress(30 * 60 * 1, d, false);
                }
            }
        });


        thread.start();

    }


    @Override
    public void run() {

    }

    public interface OnFinalize{
        public void finish();
    }

    public interface OnTextView{
        public void Update(String progress);
    }


}