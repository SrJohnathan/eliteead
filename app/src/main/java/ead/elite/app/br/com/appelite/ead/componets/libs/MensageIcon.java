package ead.elite.app.br.com.appelite.ead.componets.libs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Pc on 02/06/2016.
 */


public class MensageIcon extends FrameLayout {
private TextView textView;
    private Icon icon;

    public MensageIcon(Context context) {
        super(context);
        iniciar(context);
    }

    public MensageIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        iniciar(context);
    }

    public MensageIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniciar(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MensageIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        iniciar(context);
    }


    public  void iniciar(Context context){


        icon = new Icon(context);
        addView(icon);




    }



    public void hide(){
        icon.setVisibility(GONE);

    }

    public void show(String text){
        icon.setVisibility(GONE);
        icon.setVisibility(VISIBLE);
        icon.drawText(text);


    }



}
