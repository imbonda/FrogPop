package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.media.Media;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.screens.PlayScreen;


/**
 * This class is our game main starting point.
 */
public class FrogPop extends Game {

	public static final int VIRTUAL_WIDTH = 800;
	public static final int VIRTUAL_HEIGHT = 530;
	public static final String LOGGER_TAG = "FrogPopLogging";

	public SpriteBatch batch;
	public Media media;
	private AssetController assetController;


	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.assetController = new AssetController();
		this.assetController.loadAll();
		// Assets are now loaded.
		this.media = new Media(this.assetController);
		this.media.playMusic();
		Hud.getInstance().setBatch(this.batch);

		setScreen(new PlayScreen(this));
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
