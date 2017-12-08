package tulisandigital.com.swipe;


import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Developed with love for AMIK MBP MEDAN
 * Website : http://tulisandigital.com
 */
public class SplashScreen  extends AwesomeSplash {

    @Override
public void initSplash(ConfigSplash configSplash) {
        configSplash.setBackgroundColor(R.color.colorPrimaryDark);
        configSplash.setAnimCircularRevealDuration(4000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);
        configSplash.setLogoSplash(R.drawable.amiklogo);
        configSplash.setAnimLogoSplashDuration(4000);
        configSplash.setAnimLogoSplashTechnique(Techniques.FlipInX);
        configSplash.setOriginalHeight(400);
        configSplash.setOriginalWidth(400);
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3);
        configSplash.setPathSplashStrokeColor(R.color.colorBlueShadow);
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.colorWhite);
    configSplash.setTitleSplash("Innovation In Android Technology - AMIK MBP MEDAN");
    configSplash.setTitleTextColor(R.color.colorLink);
    configSplash.setTitleTextSize(15f); //float value
    configSplash.setAnimTitleDuration(4000);
    configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        }
        @Override
        public void animationsFinished() {
            Intent i=new Intent(getBaseContext(),MainActivity.class);
             startActivity(i);
            finish();

        }
}