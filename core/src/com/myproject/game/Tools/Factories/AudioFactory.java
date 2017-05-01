package com.myproject.game.Tools.Factories;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.myproject.game.MainGame;

/**
 * Created by leon on 5/1/17.
 */

public class AudioFactory {
    public final static Music music = createMusic();
    public final static Sound jumpSound = createJumpSound();

    private static Music createMusic() {
        Music music = MainGame.manager.get("audio/music/music.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        return music;
    }

    private static Sound createJumpSound() {
        return MainGame.manager.get("audio/sounds/jump1.ogg", Sound.class);
    }
}
