package ead.app.br.com.appelite.ead.componets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import ead.app.br.com.appelite.ead.R;


/**
 * Created by Pc on 26/07/2016.
 */
public class AnomationFab {


    public static void animateFab(final Context context, final int position, final FloatingActionButton fab) {

        Drawable drawable = new IconicsDrawable(context).sizeDp(30).color(Color.WHITE).icon(CommunityMaterial.Icon.cmd_magnify);


        final int[] colorIntArray = {R.color.md_red_500, R.color.colorPrimaryDark, R.color.md_amber_200,R.color.md_green_500,R.color.md_grey_500,
                R.color.md_brown_500,R.color.md_orange_500,R.color.md_pink_700,R.color.md_cyan_500,R.color.md_light_green_500,
                R.color.md_red_500, R.color.colorPrimaryDark, R.color.md_amber_200,R.color.md_green_500,R.color.md_grey_500,
                R.color.md_brown_500, R.color.md_orange_500,R.color.md_pink_700,R.color.md_cyan_500,R.color.md_light_green_500};
        final Drawable[] iconIntArray = {drawable, drawable, drawable};

        fab.clearAnimation();
        // Scale down animation
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon


                fab.setBackgroundTintList(context.getResources().getColorStateList(colorIntArray[position]));
              //  fab.setImageDrawable(iconIntArray[position]);

                // Scale up animation
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(shrink);
    }
}
