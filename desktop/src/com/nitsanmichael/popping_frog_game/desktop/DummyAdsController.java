package com.nitsanmichael.popping_frog_game.desktop;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public class DummyAdsController implements com.nitsanmichael.popping_frog_game.adds.AdsController {

    @Override
    public boolean isInternetConnected() {
        return false;
    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }

    @Override
    public void showInterstitialAd(Runnable then) {

    }

    @Override
    public void showRewardingVideo(Runnable then) {

    }

}
