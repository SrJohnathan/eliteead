package ead.app.br.com.appelite.ead.aplication;

import android.app.Application;

import com.facebook.FacebookSdk;


/**
 * Created by PC on 10/03/2016.
 */
public class App extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

    }


}
