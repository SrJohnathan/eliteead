package ead.elite.app.br.com.appelite.ead.componets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by PC on 17/02/2016.
 */
public class MyTextViewArial extends TextView {

    public MyTextViewArial(Context context) {
        super(context);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/arial_black.ttf");
        setTypeface(typeface);
    }

    public MyTextViewArial(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/arial_black.ttf");
        setTypeface(typeface);
    }


}
