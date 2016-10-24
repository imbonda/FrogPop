package com.nitsanmichael.popping_frog_game;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.nitsanmichael.popping_frog_game.adds.AdsController;


/**
 * Created by MichaelBond on 10/8/2016.
 */
public class AndroidAdsController implements AdsController {

    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-9580777050562768/7512736139";
    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-5519384153835422/6795093799";
    private static final String BANNER_AD_TEST_DEVICE_IDS [] = {
            "E5DBDD7696804F8E99991CB332E32029",
            "870C561BBC76ED46228B771081A24D17"
    };
    private static final int INTERNET_CONNECTION_TYPES [] = {
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_MOBILE
    };

    private AndroidLauncher mainActivity;
    private View gameView;
    private AdView bannerAd;
    private InterstitialAd interstitialAd;


    public AndroidAdsController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setUp(View gameView) {
        this.gameView = gameView;
        setupAds();
        setLayout();
    }

    private void setupAds() {
        bannerAd = new AdView(this.mainActivity);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
        interstitialAd = new InterstitialAd(this.mainActivity);
        interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);

        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest ad = builder.build();
        interstitialAd.loadAd(ad);
    }

    private void setLayout() {
        // Define the layout.
        RelativeLayout layout = new RelativeLayout(this.mainActivity);
        layout.addView(this.gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(this.bannerAd, params);

        this.mainActivity.setContentView(layout);
    }

    @Override
    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.mainActivity.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            for (int type : INTERNET_CONNECTION_TYPES) {
                if (type == activeNetwork.getType()) {
                    // connected to wifi
                    return true;
                }
            }
        }
        // not connected to the internet
        return false;
    }

    @Override
    public void hideBannerAd() {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void showBannerAd() {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                // Test devices.
                for (String deviceId : BANNER_AD_TEST_DEVICE_IDS) {
                    builder.addTestDevice(deviceId);
                }
                AdRequest ad = builder.build();
                bannerAd.loadAd(ad);
            }
        });
    }
    @Override
    public void showInterstitialAd(final Runnable then) {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (then != null) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Gdx.app.postRunnable(then);
                            AdRequest.Builder builder = new AdRequest.Builder();
                            for (String deviceId : BANNER_AD_TEST_DEVICE_IDS) {
                                builder.addTestDevice(deviceId);
                            }
                            AdRequest ad = builder.build();
                            interstitialAd.loadAd(ad);
                        }
                    });
                }
                interstitialAd.show();
            }
        });
    }
}
