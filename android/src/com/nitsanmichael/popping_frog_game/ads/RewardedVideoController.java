package com.nitsanmichael.popping_frog_game.ads;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.nitsanmichael.popping_frog_game.AndroidLauncher;


/**
 * Created by MichaelBond on 10/25/2016.
 */
public class RewardedVideoController implements RewardedVideoAdListener {

    private static final String REWARDED_VIDEO_UNIT_ID = "ca-app-pub-9580777050562768/6126304134";

    private enum LoadingState { LOADING, LOADED, FAILED, NOT_LOADED }

    private AndroidLauncher mainActivity;
    private RewardedVideoAd rewardedVideoAd;
    private Runnable rewardedVideoRunnable;
    private LoadingState state;


    public RewardedVideoController(AndroidLauncher mainActivity) {
        this.state = LoadingState.NOT_LOADED;
        this.mainActivity = mainActivity;
        this.rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.mainActivity);
        this.rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        if (LoadingState.LOADED != this.state &&
                    LoadingState.LOADING != this.state &&
                    this.mainActivity.isInternetConnected()) {
            this.state = LoadingState.LOADING;
            this.rewardedVideoAd.loadAd(REWARDED_VIDEO_UNIT_ID, new AdRequest.Builder().build());
        }
    }

    public void showRewardingVideo(Runnable then) {
        this.rewardedVideoRunnable = then;
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
            }
        });
    }

    public boolean isVideoLoaded() {
        if (LoadingState.LOADED == state) {
            return true;
        }
        else {
            this.mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadRewardedVideoAd();
                }
            });
            return false;
        }
    }

    public void onPause() {
        this.rewardedVideoAd.pause(this.mainActivity);
    }

    public void onResume() {
        this.rewardedVideoAd.pause(this.mainActivity);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        if (this.rewardedVideoRunnable != null) {
            Gdx.app.postRunnable(this.rewardedVideoRunnable);
            this.rewardedVideoRunnable = null;
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        this.state = LoadingState.LOADED;
    }

    @Override
    public void onRewardedVideoAdOpened() {
        this.state = LoadingState.NOT_LOADED;
    }

    @Override
    public void onRewardedVideoStarted() {
        this.state = LoadingState.NOT_LOADED;
    }

    @Override
    public void onRewardedVideoAdClosed() {
        this.state = LoadingState.NOT_LOADED;
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        this.state = LoadingState.NOT_LOADED;
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        this.state = LoadingState.FAILED;
    }

}
