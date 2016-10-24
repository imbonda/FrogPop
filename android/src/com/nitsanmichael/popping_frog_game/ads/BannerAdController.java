package com.nitsanmichael.popping_frog_game.ads;

import android.view.View;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;


/**
 * Created by MichaelBond on 10/25/2016.
 */
public class BannerAdController {

    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-9580777050562768/7512736139";

    private AndroidLauncher mainActivity;
    private AdView bannerAd;


    public BannerAdController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
        this.bannerAd = new AdView(this.mainActivity);
        this.bannerAd.setVisibility(View.INVISIBLE);
        this.bannerAd.setBackgroundColor(0xff000000); // black
        this.bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        this.bannerAd.setAdSize(AdSize.SMART_BANNER);
        this.bannerAd.loadAd(AndroidAdsController.getAdRequest());
    }

    public AdView getBannerAd() {
        return bannerAd;
    }

    public void setBannerAd(AdView bannerAd) {
        this.bannerAd = bannerAd;
    }

    public void hideBannerAd() {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showBannerAd() {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
            }
        });
    }

}
