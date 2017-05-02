package com.myproject.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.myproject.game.MainGame;
import com.myproject.game.Screens.PlayScreen;
import com.myproject.game.Tools.Factories.AnimationCreator;
import com.myproject.game.Tools.Factories.AudioFactory;

/**
 * Created by Brutal on 26/01/2017.
 */

public class Player extends Actor {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING}

    public State currentState = State.STANDING;
    public State previousState = currentState;
    private TextureRegion playerStand;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private Animation<TextureRegion> playerAttack;


    public Player(PlayScreen screen) {
        super(screen);
        AnimationCreator fc = new AnimationCreator(screen);
        this.playerRun = fc.createPlayerRunAnimation();
        this.playerJump = fc.createPlayerJumpAnimation();
        this.playerAttack = fc.createPlayerAttackAnimation();
        this.playerStand = fc.createPlayerStandTexture();

        setBounds(0, 0, 140 / MainGame.PPM, 140 / MainGame.PPM);
        setRegion(playerStand);
    }

    public void update(float dt) {
        setPosition(getPositionX() - getWidth() / 2, getPositionY() - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        currentState = getState();

        switch (currentState) {
            case JUMPING:
            case FALLING:
                region = playerJump.getKeyFrame(stateTime, false);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTime, true);
                break;
            case ATTACKING:
                region = playerAttack.getKeyFrame(stateTime, true);
                break;

            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if (isMovingLeft() && !region.isFlipX()) {
            region.flip(true, false);
        } else if (isMovingRight() && region.isFlipX()) {
            region.flip(true, false);
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        boolean wasJumping = previousState == State.JUMPING;

        if (isMovingUp() || isMovingDown() && wasJumping)
            return State.JUMPING;
        else if (isMovingDown())
            return State.FALLING;
        else if (isRunning())
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void jump() {
        if (!isJumping()) {
            AudioFactory.jumpSound.play();
            moveBody(0,6);
            currentState = State.JUMPING;
        }
    }

    public boolean isJumping() {
        return this.currentState == State.JUMPING;
    }


    @Override
    protected Body getBodyDefinition() {
        float mainGamePpm = MainGame.PPM;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(1024 / mainGamePpm, 1024 / mainGamePpm);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.3f, 0.6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = MainGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = MainGame.GROUND_BIT |
                MainGame.BRICK_BIT |
                MainGame.COIN_BIT |
                MainGame.ENEMY_BIT |
                MainGame.OBJECT_BIT |
                MainGame.ENEMY_HEAD_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / mainGamePpm, 6 / mainGamePpm), new Vector2(2 / mainGamePpm, 6 / mainGamePpm));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("head");
        return body;
    }

    @Override
    public void moveRight() {
        if (isHorizontalVelocityLessThan(8)) { // limit right-moving run speed
            moveBody(0.3f, 0);
        }
    }

    @Override
    public void moveLeft() {
        if (isHorizontalVelocityLessThan(-8)) { // limit left-moving run speed
            moveBody(-0.3f, 0);
        }
    }
}