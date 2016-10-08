package ead.elite.app.br.com.appelite.ead.net.volley;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by JOHNATHAN on 20/11/2015.
 */
public class VerificaNet {


    public static boolean verificaStatusWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting() && cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable()) {

            return true;
        } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting() && cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable()) {
            return true;
        } else {
            return false;
        }
    }


}