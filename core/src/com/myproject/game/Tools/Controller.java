package com.myproject.game.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myproject.game.MainGame;



public class Controller {

    private final Viewport viewport;
    private final Stage stage;
    private final TouchScreenButton buttonUp;
    private final TouchScreenButton buttonLeft;
    private final TouchScreenButton buttonRight;
    private final OrthographicCamera camera;
    private final Table sticksTable;
    private final Input input = Gdx.input;

    //Constructor.
    public Controller(SpriteBatch spriteBatch) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        this.stage = new Stage(viewport, spriteBatch);
        input.setInputProcessor(stage);

        //Buttons with images
        this.buttonUp = new TouchScreenButton("buttonup.png", 200, 200);
        this.buttonLeft = new TouchScreenButton("buttonleft.png", 200, 200);
        this.buttonRight = new TouchScreenButton("buttonright.png", 200, 200);

        //button images
        Image imageButtonUp = buttonUp.getImage();
        Image imageButtonLeft = buttonLeft.getImage();
        Image imageButtonRight = buttonRight.getImage();

        //Tabla de la cruceta.
        sticksTable = new Table();
        sticksTable.left().bottom(); //Align to the left bottom.

        // add left button to sticks table
        sticksTable.add().size(40,1);
        sticksTable.add(imageButtonLeft).size(imageButtonLeft.getWidth(), imageButtonLeft.getHeight());

        // add right button to sticks table
        sticksTable.add().size(30,1);
        sticksTable.add(imageButtonRight).size(imageButtonRight.getWidth(), imageButtonRight.getHeight());

        //
        sticksTable.add().size(Gdx.graphics.getWidth()-imageButtonLeft.getWidth()-imageButtonRight.getWidth()-imageButtonUp.getWidth()-70, 1);
        sticksTable.add(imageButtonUp).size(imageButtonUp.getWidth(), imageButtonUp.getHeight());



        stage.addActor(sticksTable);
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    //Getters.
    public boolean isUpPressed() {
        boolean touchScreenIsPressed = buttonUp.isPressed();
        boolean keyboardIsPressed = input.isKeyPressed(Keys.UP);

        return touchScreenIsPressed || keyboardIsPressed;
    }

    // TODO - implement logic
    public boolean isLeftPressed() {
        boolean touchScreenIsPressed = false;
        boolean keyboardIsPressed = false;

        return touchScreenIsPressed || keyboardIsPressed;
    }

    // TODO - implement logic
    public boolean isRightPressed() {
        boolean touchScreenIsPressed = false;
        boolean keyboardIsPressed = false;

        return touchScreenIsPressed || keyboardIsPressed;
    }

    // TODO - implement logic
    public boolean isPausePressed() {
        boolean keyboardIsPressed = false;
        return keyboardIsPressed;
    }
}