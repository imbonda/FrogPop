package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.input.BackKeyInputProcessor;
import com.nitsanmichael.popping_frog_game.scenes.Manual;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 11/2/2016.
 */
public class InfoScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
    // Creators.
    private static final String PRODUCERS =
                "Producers :\tMichael Bondarevsky and Nitsan Levy.";
    private static final String PAINTERS = "Painting artist :\tMika Yosef.";
    private static final String MUSICIANS = "Music by : \tPattrick Angelo.";


    private PoppingFrog game;
    private Viewport backgroundViewport;
    private Sprite background;
    private Array<Actor> introActors;
    private boolean isShowingManual;

    private boolean isListening;
    private Stage stage;


    public InfoScreen(final PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.backgroundViewport = new StretchViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT);
        this.introActors = new Array<Actor>();
        this.isShowingManual = false;
        this.isListening = true;

        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);

        // Go back button.
        Texture backIcon = this.game.assetController.get(Assets.BACK_ICON);
        Texture backPressedIcon = this.game.assetController.get(Assets.BACK_PRESSED_ICON);
        final ToggleButton backButton = new ToggleButton(
                new Image(backIcon), new Image(backPressedIcon));
        backButton.setSize(100, 100);
        backButton.setPosition(20, 400);
        backButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (!isListening || actor != backButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                isListening = false;
                backToMenu();
            }
        });
        // Manual button.
        Texture manualIcon = this.game.assetController.get(Assets.MANUAL_ICON);
        Texture manualPressedIcon = this.game.assetController.get(Assets.MANUAL_PRESSED_ICON);
        final ToggleButton manualButton = new ToggleButton(
                    new Image(manualIcon), new Image(manualPressedIcon));
        manualButton.setSize(270, 100);
        manualButton.setPosition(270, 180);
        manualButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != manualButton || message != ToggleButtonListener.ON_TOUCH_UP ||
                            isShowingManual) {
                    return;
                }
                showManual();
            }
        });
        this.introActors.add(manualButton);

        // Produced by.
        Label.LabelStyle style = new Label.LabelStyle(font, new Color(0x42000b9f));
        Label producersLabel = new Label(PRODUCERS, style);
        producersLabel.setFontScale(0.2f);
        producersLabel.setPosition(200, 400);
        producersLabel.setHeight(50);
        this.introActors.add(producersLabel);

        // Paintings.
        Label paintersLabel = new Label(PAINTERS, style);
        paintersLabel.setFontScale(0.2f);
        paintersLabel.setPosition(200, 350);
        paintersLabel.setHeight(50);
        this.introActors.add(paintersLabel);

        // Music.
        Label musiciansLabel = new Label(MUSICIANS, style);
        musiciansLabel.setFontScale(0.2f);
        musiciansLabel.setPosition(200, 300);
        musiciansLabel.setHeight(50);
        this.introActors.add(musiciansLabel);

        setStage(producersLabel, paintersLabel, musiciansLabel, backButton, manualButton);

        this.background = new Sprite((Texture) this.game.assetController.get(Assets.MENU_BACKGROUND));

        Gdx.input.setCatchBackKey(true);
        setInputProcessor();

        game.adsController.showBannerAd();
    }

    private void setStage(Label producersLabel, Label paintersLabel, Label musiciansLabel,
                            ToggleButton backButton, ToggleButton manualButton) {
        this.stage = new Stage(new ExtendViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(producersLabel);
        this.stage.addActor(paintersLabel);
        this.stage.addActor(musiciansLabel);
        this.stage.addActor(backButton);
        this.stage.addActor(manualButton);
    }

    private void setInputProcessor() {
        BackKeyInputProcessor backKeyInputProcessor = new BackKeyInputProcessor(
                new Runnable() {
                    @Override
                    public void run() {
                        backToMenu();
                    }
                }
        );
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, backKeyInputProcessor));
    }

    private void backToMenu() {
        final PoppingFrog game = this.game;
        fadeOut(FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dispose();
                new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
            }
        });
    }

    private void showManual() {
        for (final Actor actor : this.introActors) {
            // Fade out actor.
            this.game.tweenController.actorFade(actor, 0.5f, 1, 0, 0, 0, new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    // Remove from stage
                    actor.remove();
                }
            });
        }
        Manual manual = new Manual(this.game.assetController);
        this.stage.addActor(manual);
        // Fade in manual.
        this.game.tweenController.actorFade(manual, 0.5f, 0, 1, 0, 0, null);
        this.isShowingManual = true;
    }

    @Override
    public void setScreenColor(float r, float g, float b, float a) {
        super.setScreenColor(r, g, b, a);
        for (Actor actor : this.stage.getActors()) {
            actor.getColor().a = a;
        }
        Color c = this.background.getColor();
        this.background.setColor(c.r, c.g, c.b, a);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0/255f, 163/255f, 232/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.backgroundViewport.getCamera().combined);
        this.background.draw(this.game.batch);
        this.game.batch.end();
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    private void update(float deltaTime) {
        this.stage.act(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        this.backgroundViewport.update(width, height, true);
        this.stage.getCamera().position.set(0,0,0);
        this.stage.getCamera().translate(
                PoppingFrog.VIRTUAL_WIDTH / 2,
                PoppingFrog.VIRTUAL_HEIGHT / 2,
                0);
        this.stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        this.stage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}
