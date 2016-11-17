package ead.elite.app.br.com.appelite.ead.componets.libs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Pc on 03/06/2016.
 */
public   class Icon extends View {

    private Path path;
    private Paint paint, paint2;
private String text = "00";
    float alura, largura;
    private Canvas canvas;

    public Icon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public Icon(Context context) {
        super(context);
        init(context);
    }


    public void init(Context context) {


        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        alura = getHeight() / 3;
        largura = (float) (getWidth() / 1.5);
        paint2.setTextSize(alura);
        canvas.drawCircle((float) (getHeight() / 1.5), (float) (getHeight() / 3), (float) (getHeight() / 3.5), paint);
        canvas.drawText(text,(float) (getHeight() / 2), (float) (getHeight() / 2.5),paint2);



    }
public  void drawText(String text){
    this.text = text;

}



}
