package com.myproject.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myproject.game.Scenes.Hud;
import com.myproject.game.MainGame;
import com.myproject.game.Sprites.Zombie;
import com.myproject.game.Sprites.Player;
import com.myproject.game.Tools.BodyHandler;
import com.myproject.game.Tools.Factories.B2WorldCreator;
import com.myproject.game.Tools.Controller;
import com.myproject.game.Tools.Parallax.ParallaxBackground;
import com.myproject.game.Tools.Parallax.ParallaxLayer;
import com.myproject.game.Tools.RandomUtils;
import com.myproject.game.Tools.WorldContactListener;

/**
 * Created by usuario on 26/01/2017.
 */

public class PlayScreen implements Screen {
    private MainGame game;
    private TextureAtlas[] atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    // Tiled map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Parallax
    private ParallaxBackground rbg;

    // Box2d
    private World world;
    private Box2DDebugRenderer b2dr;

    // Controles
    private Controller controller;

    // Sprites
    private Player player;
    private Zombie[] zombies;

    // Musica
    private Music music;
    private Sound jumpSound;

    private Label pause_label;
    private boolean paused;
    private int distance;
    private final Input inputHandler = Gdx.input;

    public PlayScreen(MainGame game) {
        this.game = game;
        this.atlas = game.atlas;
        this.gamecam = new OrthographicCamera();
        this.gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        this.hud = new Hud(game.batch);
        this.mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("mapa" + RandomUtils.createInteger(1, 2) + ".tmx");
        this.renderer = new OrthogonalTiledMapRenderer(getMap(), 1 / MainGame.PPM);
        this.controller = new Controller(game.batch);
        this.world = new World(new Vector2(0, -9.8f), true);
        this.b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(this);

        // SPRITES
        this.player = new Player(this);
        this.zombies = createZombies();
        this.world.setContactListener(new WorldContactListener());

        // MUSIC
        this.music = MainGame.manager.get("audio/music/music.mp3", Music.class);
        this.music.setLooping(true);
        this.music.setVolume(0.3f);
        this.music.play();

        // SOUNDS
        this.jumpSound = MainGame.manager.get("audio/sounds/jump1.ogg", Sound.class);

        this.rbg = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(atlas[2].findRegion("bg"), new Vector2(), new Vector2(0, 0)),
        }, 1000, 750, new Vector2(0, 0));

        this.pause_label = new Label("PAUSE", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/consolas.fnt"), false), Color.WHITE));
        this.distance = player.getPositionX().intValue();
    }

    public void handleInput(float dt) {
        // controles para teclado
        if (!player.isJumping()) {
            boolean isSpacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
            boolean isValidVelocity = player.isHorizontalVelocityLessThan(2);
            boolean isUpPressed = controller.isUpPressed();

            if ((isSpacePressed && isValidVelocity) || isUpPressed) {
                player.jump();
                jumpSound.play();
            }
        }

        if (player.isHorizontalVelocityLessThan(8)) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controller.isRightPressed()) {
                player.moveBody(0.3f, 0);
            }
        }

        if (player.isHorizontalVelocityGreaterThan(-8)) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controller.isLeftPressed()) {
                player.moveBody(-0.3f, 0);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused ^= true;
        }
    }


    public void update(float dt) {
        // update world 60 times per second
        world.step(dt, 6, 2);
        player.update(dt);

        // local-method variables to be used
        float playerBodyPositionX = player.getPositionX();
        float playerBodyPositionY = player.getPositionY();


        if (playerBodyPositionY < 6) {
            game.setScreen(new GameOverScreen(game));
        }

        for (Zombie zombie : zombies) {
            zombie.update(dt);
            // local-loop variables to be used
            Body zombieBody = zombie.getBody();

            if (playerBodyPositionX - zombie.getX() < 6 && playerBodyPositionX - zombie.getX() > -6) {
                if (playerBodyPositionX - zombie.getPositionX() > 0) {
                    zombie.flip(false, false);
                    if (zombie.isHorizontalVelocityLessThan(4)){
                        zombie.moveBody(0.2f,0);

                    }
                } else {
                    zombie.flip(true, false);
                    if (zombie.isHorizontalVelocityGreaterThan(-4)){
                        zombie.moveBody(-0.2f,0);
                    }
                }
            }
        }
        gamecam.position.x = playerBodyPositionX;
        gamecam.position.y = 13;
        gamecam.update();
        renderer.setView(gamecam);
    }


    @Override
    public void render(float delta) {
        // button input
        handleInput(delta);
        Stage hudStage = hud.stage;
        SpriteBatch gameBatch = game.batch;

        // it deletes pause label when game is resumed
        if (hudStage.getActors().contains(pause_label, true))
            hudStage.getActors().pop();

        if (!paused) {
            update(delta);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            rbg.render(delta);
            // render del mapa
            renderer.render();

            // render de Box2DDebugLines

            gameBatch.setProjectionMatrix(gamecam.combined);
            gameBatch.begin();
            player.draw(gameBatch);
            for (Zombie zombie : zombies) {
                zombie.draw(gameBatch);
            }
            gameBatch.end();

            // dibuja la camara del hud
            hud.setFps(Gdx.graphics.getFramesPerSecond());
            if (player.getPositionX() > distance) {
                distance = player.getPositionX().intValue();
                hud.setDistance(distance - 10);
            }
            hud.update(delta);
        } else {
            hudStage.getActors().add(pause_label);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rbg.render(delta);
        // render del mapa
        renderer.render();

        gameBatch.setProjectionMatrix(gamecam.combined);
        gameBatch.begin();
        player.draw(gameBatch);
        for (Zombie zombie : zombies) {
            zombie.draw(gameBatch);
        }
        gameBatch.end();

        // solo se muestran controles en pantalla si se ejecuta en android
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        hud.setFps(Gdx.graphics.getFramesPerSecond());
        gameBatch.setProjectionMatrix(hudStage.getCamera().combined);
        hudStage.draw();
    }


    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas[] getAtlas() {
        return atlas;
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

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    // helper function
    private Zombie[] createZombies() {
        int numZombies = 10; // number of zombies that spawn

        float[] zombie_spawns = new float[numZombies];
        zombie_spawns[0] = 24.5f;
        zombie_spawns[1] = 30;
        zombie_spawns[2] = 43;
        zombie_spawns[3] = 40.6f;
        zombie_spawns[4] = 60.7f;
        zombie_spawns[5] = 67;
        zombie_spawns[6] = 74;
        zombie_spawns[7] = 82;
        zombie_spawns[8] = 84;
        zombie_spawns[9] = 92;


        Zombie[] zombies = new Zombie[numZombies];
        for (int i = 0; i < numZombies; i++)
            zombies[i] = new Zombie(this, zombie_spawns[i], 12 + i);
        return zombies;
    }
}