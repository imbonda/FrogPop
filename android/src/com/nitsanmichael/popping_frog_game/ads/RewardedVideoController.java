package com.nitsanmichael.popping_frog_game.ads;

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

    private AndroidLauncher mainActivity;
    private RewardedVideoAd rewardedVideoAd;
    private Runnable rewardedVideoRunnable;


    public RewardedVideoController(AndroidLauncher mainActivity) {
        this.mainActivity = mainActivity;
        this.rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.mainActivity);
        this.rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        if (!this.rewardedVideoAd.isLoaded()) {
            this.rewardedVideoAd.loadAd(REWARDED_VIDEO_UNIT_ID, new AdRequest.Builder().build());
        }
    }

    public void showRewardingVideo(final Runnable then) {
        this.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rewardedVideoRunnable = then;
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
            }
        });
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
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

}
