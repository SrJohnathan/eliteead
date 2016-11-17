package ead.elite.app.br.com.appelite.ead.componets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import ead.elite.app.br.com.appelite.ead.R;


/**
 * Created by Pc on 09/10/2016.
 */

public class CustonTextView extends TextView {

    public static final String REGULAR = "fonts/SourceSansPro-Regular.ttf";
    public static final String EXTRALIGHT = "fonts/SourceSansPro-ExtraLight.ttf";
    public static final String LIGHT = "fonts/SourceSansPro-Light.ttf";
    public static final String SEMIBOLD = "fonts/SourceSansPro-Semibold.ttf";
    public static final String BOLD = "fonts/SourceSansPro-Bold.ttf";
    public static final String BLACK = "fonts/SourceSansPro-Black.ttf";
    public static final String EXTRALIGHT_ITALIC = "fonts/SourceSansPro-ExtraLightItalic.ttf";
    public static final String LIGHT_ITALIC = "fonts/SourceSansPro-LightItalic.ttf";
    public static final String SEMIBOLD_ITALIC = "fonts/SourceSansPro-SemiboldItalic.ttf";
    public static final String BOLD_ITALIC = "fonts/SourceSansPro-BoldItalic.ttf";
    public static final String BLACK_ITALIC = "fonts/SourceSansPro-BlackItalic.ttf";

    private Typeface typeface;
    private Context context;


    public CustonTextView(Context context) {
        super(context);
        init(context, null);
    }

    public CustonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustonTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attributeSet) {
        this.context = context;

        if (typeface != null) {
            setTypeface(typeface);
        } else {
            if (attributeSet != null) {

                TypedArray a = context.obtainStyledAttributes(attributeSet,
                        R.styleable.CustonTextView);

                if (a.getInteger(R.styleable.CustonTextView_setFont, 0) == 8) {
                    typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), LIGHT_ITALIC);
                    setTypeface(typeface);
                }else if (a.getInteger(R.styleable.CustonTextView_setFont, 0) == 4) {
                    typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), SEMIBOLD);
                    setTypeface(typeface);

                }else if (a.getInteger(R.styleable.CustonTextView_setFont, 0) == 10) {
                    typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), BOLD_ITALIC);
                    setTypeface(typeface);

                } else if (a.getInteger(R.styleable.CustonTextView_setFont, 0) == 6) {
                    typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), BLACK);
                    setTypeface(typeface);

                } else if (a.getInteger(R.styleable.CustonTextView_setFont, 0) == 1) {
                    typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), REGULAR);
                    setTypeface(typeface);

                }  a.recycle();

            }

        }


    }




    public void setTypeFace(String face) {
        if (!face.equalsIgnoreCase("")) {
            typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), face);

        }

    }


}

