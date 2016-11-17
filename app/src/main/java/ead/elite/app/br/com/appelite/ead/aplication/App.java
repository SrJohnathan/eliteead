package ead.elite.app.br.com.appelite.ead.aplication;

import android.app.Application;


import com.facebook.FacebookSdk;

import ead.elite.app.br.com.appelite.ead.util.IabHelper;


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
