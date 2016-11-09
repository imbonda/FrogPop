package com.nitsanmichael.popping_frog_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.config.Config;
import com.nitsanmichael.popping_frog_game.data.Data;
import com.nitsanmichael.popping_frog_game.adds.AdsController;
import com.nitsanmichael.popping_frog_game.media.Media;
import com.nitsanmichael.popping_frog_game.playservice.PlayServices;
import com.nitsanmichael.popping_frog_game.screens.FadingScreen;
import com.nitsanmichael.popping_frog_game.screens.MainMenuScreen;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;


/**
 * This class is our game main starting point.
 */
public class PoppingFrog extends Game {

	public static final int VIRTUAL_WIDTH = 800;
	public static final int VIRTUAL_HEIGHT = 530;
	public static final String GAME_TITLE = "Popping Frog";
	public static final String LOGGER_TAG = "PoppingFrogLogging";

	public AdsController adsController;
	public PlayServices playServices;
	public SpriteBatch batch;
	public Data data;
	public Media media;
	public Config config;
	public AssetController assetController;
	public TweenController tweenController;


	public PoppingFrog(AdsController adsController, PlayServices playServices) {
		this.adsController = adsController;
		this.playServices = playServices;
	}

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.data = new Data(this.playServices);
		this.assetController = new AssetController(this.data);
		this.assetController.loadAll();
		// Assets are now loaded.
		this.config = this.assetController.config;
		this.media = new Media(
					this.assetController, this.data.getMusicVolume(), this.data.getSoundVolume());
		this.tweenController = new TweenController();
		FadingScreen screen = new MainMenuScreen(PoppingFrog.this);
		screen.fadeIn(this, 0.5f);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		this.tweenController.update(deltaTime);
		if (null != this.screen) {
			this.screen.render(deltaTime);
		}
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
		this.assetController.clearAll();
	}

}
