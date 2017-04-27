package com.myproject.game.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myproject.game.MainGame;



public class Controller {

    public Viewport viewport;
    public Stage stage;
    public Button buttonUp;
    public Button buttonLeft;
    public Button buttonRight;
    public OrthographicCamera camera;
    public Table sticksTable;

    //Constructor.
    public Controller(SpriteBatch spriteBatch) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        this.stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        //Buttons with images
        this.buttonUp = new Button("buttonup.png", 200, 200);
        this.buttonLeft = new Button("buttonleft.png", 200, 200);
        this.buttonRight = new Button("buttonright.png", 200, 200);

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
        return buttonUp.isPressed();
    }

    public boolean isLeftPressed() {
        return buttonLeft.isPressed();
    }

    public boolean isRightPressed() {
        return buttonRight.isPressed();
    }

}