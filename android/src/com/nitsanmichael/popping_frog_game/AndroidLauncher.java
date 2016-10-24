package com.nitsanmichael.popping_frog_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication {

	private AndroidPlayServices playServices;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		// Create a gameView and a bannerAd AdView.
		this.playServices = new AndroidPlayServices(this);
		AndroidAdsController adsController = new AndroidAdsController(this);
		View gameView = initializeForView(new PoppingFrog(adsController, this.playServices), config);
		adsController.setUp(gameView);
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

}
