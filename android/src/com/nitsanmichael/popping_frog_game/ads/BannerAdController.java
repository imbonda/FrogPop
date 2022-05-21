package com.nitsanmichael.popping_frog_game.ads;

import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;


/**
 * Created by MichaelBond on 10/25/2016.
 */
public class BannerAdController extends AdListener {

    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-9580777050562768/7512736139";

    private AndroidLauncher mainActivity;
    private AdView bannerAd;
    private boolean isLoaded;
    private boolean isHidden;


    public BannerAdController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
        this.isLoaded = false;
        this.isHidden = false;
        this.bannerAd = new AdView(this.mainActivity);
        this.bannerAd.setVisibility(View.INVISIBLE);
        this.bannerAd.setBackgroundColor(0xff000000); // black
        this.bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        this.bannerAd.setAdSize(AdSize.SMART_BANNER);
        this.bannerAd.setAdListener(this);
        loadBannerAd();
    }

    private void loadBannerAd() {
        if (this.mainActivity.isInternetConnected() &&
                    !this.isLoaded &&
                    !this.bannerAd.isLoading()) {
            this.bannerAd.loadAd(AndroidAdsController.getAdRequest());
        }
    }

    public AdView getBannerAd() {
        return bannerAd;
    }

    public void hideBannerAd() {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isHidden = true;
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showBannerAd() {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isHidden = false;
                if (isLoaded) {
                    bannerAd.setVisibility(View.VISIBLE);
                }
                else {
                    loadBannerAd();
                }
            }
        });
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        this.isLoaded = true;
        if (!this.isHidden) {
            this.bannerAd.setVisibility(View.VISIBLE);
        }
    }
}
