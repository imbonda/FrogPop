package com.nitsanmichael.frog_pop_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nitsanmichael.frog_pop_game.config.Config;
import com.nitsanmichael.frog_pop_game.data.Data;
import com.nitsanmichael.frog_pop_game.screens.MainMenuScreen;
import com.nitsanmichael.frog_pop_game.adds.AdsController;
import com.nitsanmichael.frog_pop_game.assets.AssetController;
import com.nitsanmichael.frog_pop_game.media.Media;

/**
 * This class is our game main starting point.
 */
public class FrogPop extends Game {

	public static final int VIRTUAL_WIDTH = 800;
	public static final int VIRTUAL_HEIGHT = 530;
	public static final String LOGGER_TAG = "FrogPopLogging";

	public AdsController adsController;
	public SpriteBatch batch;
	public Data data;
	public Media media;
	public Config config;
	public AssetController assetController;


	public FrogPop(AdsController adsController) {
		this.adsController = adsController;
	}

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.assetController = new AssetController();
		this.assetController.loadAll();
		// Assets are now loaded.
		this.config = this.assetController.config;
		this.data = new Data();
		this.media = new Media(
					this.assetController, this.data.getMusicVolume(), this.data.getSoundVolume());

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
		this.assetController.clearAll();
	}

}
