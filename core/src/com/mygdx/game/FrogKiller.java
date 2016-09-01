package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import states.GameStateManager;
import states.PlayState;

public class FrogKiller extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;
	private Texture img;
	private OrthographicCamera cam;
	private Viewport viewport;
	private static final int VIRTUAL_WIDTH = 800;
	private static final int VIRTUAL_HEIGHT = 530;

	@Override
	public void create () {
	this.batch = new SpriteBatch();
		this.gsm = new GameStateManager();
		this.gsm.push(new PlayState(gsm));
		cam = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
		viewport = new FitViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,cam);
		viewport.apply();
	}
	public void resize(int width, int height) {

		viewport.update(width, height);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
	}
	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		this.gsm.setViewport(viewport);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.gsm.update(Gdx.graphics.getDeltaTime());
		this.gsm.render(this.batch);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	}
