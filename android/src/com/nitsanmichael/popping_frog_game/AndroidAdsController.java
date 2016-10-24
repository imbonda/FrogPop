package com.nitsanmichael.popping_frog_game;

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
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.nitsanmichael.popping_frog_game.adds.AdsController;
import com.vungle.publisher.VunglePub;


/**
 * Created by MichaelBond on 10/8/2016.
 */
public class AndroidAdsController implements AdsController, RewardedVideoAdListener {

    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-9580777050562768/7512736139";
    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-5519384153835422/6795093799";
    private static final String REWARDED_VIDEO_UNIT_ID = "ca-app-pub-9580777050562768/6126304134";
//    // get your App ID from the app's main page on the Vungle Dashboard after setting up your app
//    private static final String VUNGLE_APP_ID = "580e1a517ad005400b000056";

    private static final String TEST_DEVICE_IDS [] = {
            "E5DBDD7696804F8E99991CB332E32029",
            "870C561BBC76ED46228B771081A24D17"
    };
    private static final int INTERNET_CONNECTION_TYPES [] = {
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_MOBILE
    };

    private static AdRequest getAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        // Test devices.
        for (String deviceId : TEST_DEVICE_IDS) {
            builder.addTestDevice(deviceId);
        }
        return builder.build();
    }

//    // get the VunglePub instance
//    private final VunglePub vunglePub = VunglePub.getInstance();

    private AndroidLauncher mainActivity;
    private View gameView;
    private AdView bannerAd;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private Runnable rewardedVideoRunnable;


    public AndroidAdsController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
//        // initialize the Publisher SDK
//        vunglePub.init(mainActivity, VUNGLE_APP_ID);
    }

    public void setUp(View gameView) {
        this.gameView = gameView;
        setupAds();
        setLayout();
    }

    private void setupAds() {
        // Banner adds.
        this.bannerAd = new AdView(this.mainActivity);
        this.bannerAd.setVisibility(View.INVISIBLE);
        this.bannerAd.setBackgroundColor(0xff000000); // black
        this.bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        this.bannerAd.setAdSize(AdSize.SMART_BANNER);
        this.bannerAd.loadAd(getAdRequest());
        // Interstitial adds.
        this.interstitialAd = new InterstitialAd(this.mainActivity);
        this.interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        this.interstitialAd.loadAd(getAdRequest());
        // Reward video adds.
        this.rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mainActivity);
        this.rewardedVideoAd.setRewardedVideoAdListener(this);
        if (!this.rewardedVideoAd.isLoaded()) {
            this.rewardedVideoAd.loadAd(REWARDED_VIDEO_UNIT_ID, getAdRequest());
        }
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
                            interstitialAd.loadAd(getAdRequest());
                        }
                    });
                }
                interstitialAd.show();
            }
        });
    }

    @Override
    public void showRewardingVideo(final Runnable then) {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (then != null) {
                    rewardedVideoRunnable = then;
                }
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                    System.out.println("Reward Vid ISSSS Loaded !! :((( ");
                }
                else {
                    System.out.println("Reward Vid Not Loaded !! :((( ");
                }
            }
        });
//        if (vunglePub.isAdPlayable()) {
//            System.out.println("asaaaaaaaaaaaaaaaaaaaaasdasdasdasXXXporno");
//        }
//        vunglePub.playAd();
    }

    public void onPause() {
        this.rewardedVideoAd.pause(this.mainActivity);
    }

    public void onResume() {
        this.rewardedVideoAd.pause(this.mainActivity);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
