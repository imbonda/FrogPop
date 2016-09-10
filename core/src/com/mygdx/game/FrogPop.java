package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.sprites.SpritesDrawer;


public class FrogPop extends Game {

	public static final int VIRTUAL_WIDTH = 800;
	public static final int VIRTUAL_HEIGHT = 530;

	public SpriteBatch batch;


	@Override
	public void create () {
		this.batch = new SpriteBatch();
		SpritesDrawer.getInstance().setBatch(this.batch);
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
	}

}
