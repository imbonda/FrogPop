package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.adds.AdsController;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.config.Config;
import com.mygdx.game.data.Data;
import com.mygdx.game.media.Media;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.screens.SettingsScreen;


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
	private AssetController assetController;


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
		this.media = new Media(this.assetController);
		if (0 != this.data.getMusicVolume()) {
			this.media.playMusic();
		}
		Hud.getInstance().setBatch(this.batch);

		setScreen(new PlayScreen(this));
		//setScreen(new SettingsScreen(this));
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
