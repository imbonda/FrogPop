package com.nitsanmichael.popping_frog_game.ads;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;


/**
 * Created by MichaelBond on 10/25/2016.
 */
public class InterstitialAdsController extends AdListener {

    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-5519384153835422/6795093799";

    private AndroidLauncher mainActivity;
    private InterstitialAd interstitialAd;
    private Runnable interstitialAdRunnable;


    public InterstitialAdsController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
        this.interstitialAd = new InterstitialAd(this.mainActivity);
        this.interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        this.interstitialAd.setAdListener(this);
        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        if (!this.interstitialAd.isLoaded() &&
                    ! this.interstitialAd.isLoading() &&
                    this.mainActivity.isInternetConnected()) {
            interstitialAd.loadAd(AndroidAdsController.getAdRequest());
        }
    }

    public void showInterstitialAd(final Runnable then) {
        this.interstitialAdRunnable = then;
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadInterstitialAd();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        loadInterstitialAd();
        if (this.interstitialAdRunnable != null) {
            Gdx.app.postRunnable(this.interstitialAdRunnable);
            this.interstitialAdRunnable = null;
        }
    }

    @Override
    public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
    }
}
