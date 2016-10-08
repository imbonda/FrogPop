package com.nitsanmichael.popping_frog_game;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication  implements com.nitsanmichael.popping_frog_game.adds.AdsController {

	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-9580777050562768/7512736139";
	private static final String BANNER_AD_TEST_DEVICE_IDS [] = {
			"E5DBDD7696804F8E99991CB332E32029",
			"870C561BBC76ED46228B771081A24D17"
	};
	private static final int INTERNET_CONNECTION_TYPES [] = {
			ConnectivityManager.TYPE_WIFI,
			ConnectivityManager.TYPE_MOBILE
	};

	private AdView bannerAd;
	private View gameView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		// Create a gameView and a bannerAd AdView.
		this.gameView = initializeForView(new PoppingFrog(this), config);
		setupAds();
		setLayout();
	}

	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}

	public void setLayout() {
		// Define the layout.
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(this.gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(this.bannerAd, params);

		setContentView(layout);
	}

	@Override
	public boolean isInternetConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
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
}
