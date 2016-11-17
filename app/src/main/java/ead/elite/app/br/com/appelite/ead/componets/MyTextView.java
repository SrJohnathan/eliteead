package ead.elite.app.br.com.appelite.ead.componets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by PC on 17/02/2016.
 */
public class MyTextView extends TextView  {

    public MyTextView(Context context) {
        super(context);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private   void init(Context context){
        try {
            Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/seguisli.ttf");
            setTypeface(typeface);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
