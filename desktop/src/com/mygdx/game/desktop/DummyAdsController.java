package com.mygdx.game.desktop;

import com.mygdx.game.adds.AdsController;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public class DummyAdsController implements AdsController {

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

}
