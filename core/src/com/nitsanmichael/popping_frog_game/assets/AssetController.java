package com.nitsanmichael.popping_frog_game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.animation.Animation;
import com.nitsanmichael.popping_frog_game.config.Config;
import com.nitsanmichael.popping_frog_game.data.Data;

import java.util.HashMap;

/**
 * Created by MichaelBond on 9/27/2016.
 */
public class AssetController {

    // Public members.
    public Config config;
    // Private members.
    private Data data;
    private AssetManager manager;
    // Animations frames.
    private HashMap<String[], Array<Texture>> animationFramesHashMap;

    public AssetController(Data data) {
        this.data = data;
        this.manager = new AssetManager();
        this.animationFramesHashMap = new HashMap<String[], Array<Texture>>();
    }


    public void loadAll() {
        // todo
        loadMusics();
        loadSounds();
        loadFonts();
        loadSkins();
        loadTextures();
        loadParticleEffects();
        this.manager.finishLoading();
        // Load config.
        this.config = new Config();
        this.config.load();
    }

    private void loadMusics() {
        for (String fileName : Assets.MUSIC_FILES) {
            this.manager.load(fileName, Music.class);
        }
    }

    private void loadSounds() {
        for (String fileName : Assets.SOUND_FILES) {
            this.manager.load(fileName, Sound.class);
        }
    }

    private void loadFonts() {
        for (String fileName : Assets.FONT_FILES) {
            this.manager.load(fileName, BitmapFont.class);
        }
    }

    private void loadSkins() {
        for (String fileName : Assets.SKIN_FILES) {
            this.manager.load(fileName, Skin.class);
        }
    }

    private void loadTextures() {
        for (String fileName : Assets.TEXTURE_FILES) {
            this.manager.load(fileName, Texture.class);
        }
    }

    private void loadParticleEffects() {
        for (Assets.EffectMeta effect : Assets.PARTICLE_EFFECTS) {
            this.manager.load(effect.fileName, ParticleEffect.class);
        }
    }

    public <T> T get(String fileName) {
        return this.manager.get(fileName);
    }

    public Animation getAnimation(String [] animationTexturesNames) {
        return new Animation(getFrames(animationTexturesNames));
    }

    public Animation getAnimation(String [] animationTexturesNames, float frameTime) {
        return new Animation(getFrames(animationTexturesNames), frameTime);
    }

    private Array<Texture> getFrames(String [] animationTexturesNames) {
        if (this.animationFramesHashMap.containsKey(animationTexturesNames)) {
            return this.animationFramesHashMap.get(animationTexturesNames);
        }
        else {
            Array<Texture> frames = new Array<Texture>();
            for (String textureName : animationTexturesNames) {
                Texture frame = get(textureName);
                frames.add(frame);
            }
            this.animationFramesHashMap.put(animationTexturesNames, frames);
            return frames;
        }
    }

    public Array<Animation> getHeroAnimations() {
        Array<Animation> heroAnimations = new Array<Animation>();
        int heroIndex = this.data.getHeroIndex();
        for (Assets.AnimationMeta meta : Assets.HEROES_ANIMATIONS[heroIndex]) {
            heroAnimations.add(getAnimation(meta.animation, meta.frameTime));
        }
        return heroAnimations;
    }

    public void unload(String fileName) {
        this.manager.unload(fileName);
    }

    public void clearAll() {
        this.manager.clear();
        this.animationFramesHashMap.clear();
    }
}
