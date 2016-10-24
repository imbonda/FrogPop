package com.nitsanmichael.popping_frog_game.ads;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;


/**
 * Created by MichaelBond on 10/25/2016.
 */
public class InterstitialAdsController {

    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-5519384153835422/6795093799";

    private AndroidLauncher mainActivity;
    private InterstitialAd interstitialAd;


    public InterstitialAdsController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
        this.interstitialAd = new InterstitialAd(this.mainActivity);
        this.interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        this.interstitialAd.loadAd(AndroidAdsController.getAdRequest());
    }

    public void showInterstitialAd(final Runnable then) {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (then != null) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Gdx.app.postRunnable(then);
                            interstitialAd.loadAd(AndroidAdsController.getAdRequest());
                        }
                    });
                }
                interstitialAd.show();
            }
        });
    }

}
