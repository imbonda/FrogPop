package com.nitsanmichael.popping_frog_game.adds;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public interface AdsController {

    boolean isInternetConnected();

    void showBannerAd();

    void hideBannerAd();

    void showInterstitialAd (Runnable then);
}
