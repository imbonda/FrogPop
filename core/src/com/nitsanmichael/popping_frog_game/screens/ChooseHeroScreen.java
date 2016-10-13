package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.sprites.Buttons;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleBritishFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleMexicanFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleRegularFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleTurkishFrog;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class ChooseHeroScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final String CHOOSE_HERO_TITLE = "My Frogish Hero";

    private Texture backgroundTexture;
    private PoppingFrog game;
    private Array<IdleFrog> idleFrogs;
    private IdleFrog frog;
    private int index;
    private Stage stage;


    public ChooseHeroScreen(PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);

        // Go back button.
        Texture backIcon = this.game.assetController.get(Assets.BACK_ICON);
        Texture backPressedIcon = this.game.assetController.get(Assets.BACK_PRESSED_ICON);
        final ToggleButton backButton = new ToggleButton(
                new Image(backIcon), new Image(backPressedIcon));
        backButton.setSize(100, 100);
        backButton.setPosition(20, 400);
        backButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                final PoppingFrog game = ChooseHeroScreen.this.game;
                ChooseHeroScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
            }
        });

        // Next hero button.
        Texture nextIcon = this.game.assetController.get(Assets.NEXT_ICON);
        Texture nextPressedIcon = this.game.assetController.get(Assets.NEXT_PRESSED_ICON);
        final ToggleButton nextHeroButton = new ToggleButton(
                    new Image(nextIcon), new Image(nextPressedIcon));
        nextHeroButton.setSize(80, 80);
        nextHeroButton.setPosition(540, 240);
        nextHeroButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                nextHeroButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                nextHeroButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                // todo old
                index++;
                frog = idleFrogs.get(index%4);
            }
        });

        // Choose hero title.
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.DARK_GRAY);
        Label chooseHeroTitle = new Label(CHOOSE_HERO_TITLE, labelStyle);
        chooseHeroTitle.setFontScale(0.5f);
        chooseHeroTitle.setPosition(220, 440);
        chooseHeroTitle.setHeight(50);

        initIdleFrogs();
        this.index = 0;
        this.frog = idleFrogs.get(index);

        setStage(chooseHeroTitle, backButton, nextHeroButton);
        this.backgroundTexture = this.game.assetController.get(Assets.MENU_BACKGROUND);

        if (game.adsController.isInternetConnected()) {
            game.adsController.showBannerAd();
        }
    }

    private void initIdleFrogs() {
        this.idleFrogs = new Array<IdleFrog>();
        this.idleFrogs.add(new IdleRegularFrog(this.game.assetController,
                    IdleRegularFrog.AnimationType.TONGUE, new Vector2(400, 200)));
        this.idleFrogs.add(new IdleBritishFrog(this.game.assetController, new Vector2(400, 200)));
        this.idleFrogs.add(new IdleMexicanFrog(this.game.assetController, new Vector2(400, 200)));
        this.idleFrogs.add(new IdleTurkishFrog(this.game.assetController, new Vector2(400, 200)));
    }

    private void setStage(Label chooseHeroTitle, ToggleButton backButton,
                            ToggleButton nextHeroButton) {
        this.stage = new Stage(new FitViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                    this.game.batch);
        this.stage.addActor(chooseHeroTitle);
        this.stage.addActor(backButton);
        this.stage.addActor(nextHeroButton);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void setScreenColor(float r, float g, float b, float a) {
        super.setScreenColor(r, g, b, a);
        for (Actor actor : this.stage.getActors()) {
            actor.getColor().a = a;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0/255f, 163/255f, 232/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.game.batch.begin();
        this.game.batch.draw(this.backgroundTexture, 0, 0);
        drawIdleFrogs();
        this.game.batch.end();
        this.stage.draw();
    }

    public void update(float deltaTime) {
        updateIdleFrogs(deltaTime);
    }

    private void updateIdleFrogs(float deltaTime) {
        for (IdleFrog frog : this.idleFrogs) {
            frog.update(deltaTime);
        }
    }

    private void drawIdleFrogs() {
        frog.draw(this.game.batch);
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
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
