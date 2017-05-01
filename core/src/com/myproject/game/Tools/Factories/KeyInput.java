package com.myproject.game.Tools.Factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by leon on 4/27/17.
 */

public class KeyInput {
    private final Input input = Gdx.input;

    private boolean isPressed(int key) {
        return input.isKeyPressed(key);
    }
}
