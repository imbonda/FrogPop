package com.nitsanmichael.popping_frog_game.ads;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;
import com.nitsanmichael.popping_frog_game.adds.AdsController;


/**
 * Created by MichaelBond on 10/8/2016.
 */
public class AndroidAdsController implements AdsController {

    private static final String APP_ID = "ca-app-pub-9580777050562768~5793827338";

    private static final String TEST_DEVICE_IDS [] = {
            "E5DBDD7696804F8E99991CB332E32029",
            "870C561BBC76ED46228B771081A24D17"
    };
    private static final int INTERNET_CONNECTION_TYPES [] = {
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_MOBILE
    };

    public static AdRequest getAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        // Test devices.
        for (String deviceId : TEST_DEVICE_IDS) {
            builder.addTestDevice(deviceId);
        }
        return builder.build();
    }

    private AndroidLauncher mainActivity;
    private View gameView;
    private BannerAdController bannerAdController;
    private InterstitialAdsController interstitialAdsController;
    private RewardedVideoController rewardedVideoController;


    public AndroidAdsController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setUp(View gameView) {
        this.gameView = gameView;
        setupAds();
        setLayout();
    }

    private void setupAds() {
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this.mainActivity, APP_ID);
        // Banner adds.
        this.bannerAdController = new BannerAdController(this.mainActivity);
        // Interstitial adds.
        this.interstitialAdsController = new InterstitialAdsController(this.mainActivity);
        // Reward video adds.
        this.rewardedVideoController = new RewardedVideoController(this.mainActivity);
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
        layout.addView(this.bannerAdController.getBannerAd(), params);

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
        this.bannerAdController.hideBannerAd();
    }

    @Override
    public void showBannerAd() {
        this.bannerAdController.showBannerAd();
    }

    @Override
    public void showInterstitialAd(final Runnable then) {
        this.interstitialAdsController.showInterstitialAd(then);
    }

    @Override
    public boolean isRewardedVideoAvailable() {
        return this.rewardedVideoController.isVideoLoaded();
    }

    @Override
    public void showRewardingVideo(final Runnable then) {
        this.rewardedVideoController.showRewardingVideo(then);
    }

    public void onPause() {
        this.rewardedVideoController.onPause();
    }

    public void onResume() {
        this.rewardedVideoController.onResume();
    }
}
