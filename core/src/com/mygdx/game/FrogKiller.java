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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import states.GameStateManager;
import states.PlayState;

public class FrogKiller extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;
	private OrthographicCamera cam;
	//private Rectangle viewport;
	private static final int VIRTUAL_WIDTH = 800;
	private static final int VIRTUAL_HEIGHT = 530;
	private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.gsm = new GameStateManager();
		this.gsm.push(new PlayState(gsm));

	}
	@Override
	public void render () {
		//Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.gsm.update(Gdx.graphics.getDeltaTime());
		this.gsm.render(this.batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	//public void resize(int width, int height)
	//{
	//	float aspectRatio = (float)width/(float)height;
	//	float scale = 1f;
	//	Vector2 crop = new Vector2(0f, 0f);
	//	if(aspectRatio > ASPECT_RATIO)
	//	{
	//		scale = (float)height/(float)VIRTUAL_HEIGHT;
	//		crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
	//	}
	//	else if(aspectRatio < ASPECT_RATIO)
	//	{
	//		scale = (float)width/(float)VIRTUAL_WIDTH;
	//		crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
	//	}
	//	else
	//	{
	//		scale = (float)width/(float)VIRTUAL_WIDTH;
	//	}

	//	float w = (float)VIRTUAL_WIDTH*scale;
	//	float h = (float)VIRTUAL_HEIGHT*scale;
	//	viewport = new Rectangle(crop.x, crop.y, w, h);
	//}
	}
