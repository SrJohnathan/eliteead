package ead.elite.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;


/**
 * Created by JOHNATHAN on 14/01/2016.
 */
public class Tabs extends FragmentPagerAdapter {
    private Context mContext;
    private Drawable[] icon;
    private int heit;

    private ArrayList<String> tabs;

    public Tabs(FragmentManager fm, Context c,ArrayList<String> tabs) {
        super(fm);
        mContext = c;
        this.tabs = tabs;
        double scala = mContext.getResources().getDisplayMetrics().density;
        heit = (int) (24 * scala + 0.5f);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle b = new Bundle();


        if (position == 0) {
            fragment = new Cursos();
            b.putInt("ind", position);
            fragment.setArguments(b);
        }

        if (position == 1) {
            fragment = new BuscarCate();
            b.putStringArrayList("neme",tabs);
            fragment.setArguments(b);
        }

        if (position > 1) {
            fragment = new CategoriaFrag();
            b.putString("tabs",tabs.get(position));
            fragment.setArguments(b);
        }





        return fragment;

    }


    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      /*  icon = new Drawable[]{new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_facebook_box).sizeDp(15).color(Color.WHITE),
                new IconicsDrawable(mContext, CommunityMaterial.Icon.cmd_email).sizeDp(15).color(Color.WHITE)};
        Drawable drawable = icon[position];
        drawable.setBounds(0, 0, heit,heit);

        ImageSpan span = new ImageSpan(drawable);

        SpannableString string = new SpannableString(" ");
        string.setSpan(span, 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); */

       // return (string);
          return (tabs.get(position));
    }
}
