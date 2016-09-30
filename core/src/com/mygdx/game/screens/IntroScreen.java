package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.scenes.ToggleButton;
import com.mygdx.game.scenes.events.MessageEventListener;

/**
 * Created by MichaelBond on 9/29/2016.
 */
public class IntroScreen implements Screen {

    private static final String MUSIC_ON = "MUSIC ON";
    private static final String MUSIC_OFF = "MUSIC_OFF";
    private static final String SOUND_ON = "SOUND ON";
    private static final String SOUND_OFF = "SOUND_OFF";

//    private Texture musicTexture;
//    private Texture soundTexture;
    private ToggleButton musicButton;
    private ToggleButton soundButton;
    private FrogPop game;
    private Stage stage;

    public IntroScreen(FrogPop game) {
        this.game = game;
//        this.musicTexture = new Texture();
//        this.soundTexture = new Texture();
        this.musicButton = new ToggleButton(
                    new Image(new Texture("sound.png")),
                    new Image(new Texture("sound_mute.png")));
//        this.musicButton.setY(200);
//        this.musicButton.setX(100);
        int musicState = (this.game.data.isMusicMuted()) ?
                    (ToggleButton.OFF_STATE) : (ToggleButton.ON_STATE);
        this.musicButton.setState(musicState);
        this.musicButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if(message == ToggleButton.ON_STATE) {
                    IntroScreen.this.game.data.setMusicMute(false);
                    IntroScreen.this.game.media.playMusic();
                }
                else if(message == ToggleButton.OFF_STATE) {
                    IntroScreen.this.game.data.setMusicMute(true);
                    IntroScreen.this.game.media.stopMusic();
                }
            }
        });
        this.stage = new Stage(
                    new FitViewport(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera()),
                    this.game.batch);
        this.stage.addActor(this.musicButton);
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }
}
