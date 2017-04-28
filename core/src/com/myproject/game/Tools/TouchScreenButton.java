package com.myproject.game.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by leon on 4/27/17.
 */

public class TouchScreenButton {
    private final Image image;
    private boolean isPressed;

    public TouchScreenButton(String spriteName, int width, int height) {
        this.image = new Image(new Texture(spriteName));
        this.image.setSize(width, height);
        this.image.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = false;
            }
        });
    }

    public Image getImage() {
        return this.image;

    }

    public boolean isPressed() {
        return this.isPressed;
    }
}
