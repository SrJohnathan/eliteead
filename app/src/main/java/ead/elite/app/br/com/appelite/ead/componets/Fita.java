package ead.elite.app.br.com.appelite.ead.componets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import ead.elite.app.br.com.appelite.ead.R;

/**
 * Created by Pc on 18/06/2016.
 */
public class Fita extends View {

    private Path path;
    private Paint paint ,paint1,paint2;
    private Canvas canvas;
    float  alura ;
    float largura ;
    Context context;
String string = "xx";

    public Fita(Context context) {
        super(context);
        init(context);
    }

    public Fita(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){

        path = new Path();

        this.context = context;
        paint = new Paint();

         paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);

        paint2.setColor(Color.WHITE);
        paint2.setTextSize((float) (alura / 1.5));


        paint.setStyle(Paint.Style.STROKE);
        paint.setColorFilter(new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN));
        paint.setStrokeWidth(2);
         paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(Color.LTGRAY);
        CornerPathEffect effect = new CornerPathEffect(5);
        paint.setPathEffect(effect);
        paint1.setPathEffect(effect);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

      float  alura = getHeight() / 1;
        float largura =  getWidth() / 1;

        this.alura = alura;
        this.largura = largura;


        path.moveTo(largura,0);
        path.lineTo(0,0);
        path.lineTo(20,(alura/2));
        path.lineTo(0,alura);
        path.lineTo( largura,alura);
        path.lineTo(largura,0);
        path.close();


        this.canvas = canvas;

        canvas.drawPath(path,paint1);
        canvas.drawPath(path,paint);
        canvas.drawText(string,(float) (largura/3),(float)(alura/1.5),paint2);


    }

    public void text(String s,int colo){
        string = s;
        paint1.setColor(colo);








    }



}
