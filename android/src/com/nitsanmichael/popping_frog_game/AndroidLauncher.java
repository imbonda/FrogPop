package com.nitsanmichael.popping_frog_game;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nitsanmichael.popping_frog_game.ads.AndroidAdsController;


public class AndroidLauncher extends AndroidApplication {

	private static final int INTERNET_CONNECTION_TYPES [] = {
			ConnectivityManager.TYPE_WIFI,
			ConnectivityManager.TYPE_MOBILE
	};

	private AndroidPlayServices playServices;
	private AndroidAdsController adsController;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		// Create a gameView and a bannerAd AdView.
		this.playServices = new AndroidPlayServices(this);
		this.adsController = new AndroidAdsController(this);
		View gameView = initializeForView(new PoppingFrog(this.adsController, this.playServices), config);
		this.adsController.setUp(gameView);
	}

	public boolean isInternetConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(
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
	protected void onStart() {
		super.onStart();
		this.playServices.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		this.playServices.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		this.playServices.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.adsController.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.adsController.onResume();
	}
}
