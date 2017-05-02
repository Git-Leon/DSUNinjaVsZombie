package com.myproject.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.myproject.game.Screens.PlayScreen;

/**
 * Created by leon on 4/28/17.
 */

abstract public class Actor extends Sprite {
    protected final World world;
    protected final PlayScreen screen;
    protected final Body body;
    protected float stateTime = 0;

    public Actor(PlayScreen screen) {
        this(screen, 0, 0);
    }

    public Actor(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        this.body = getBodyDefinition();
    }

    abstract protected Body getBodyDefinition();

    public World getWorld() {
        return world;
    }

    public PlayScreen getScreen() {
        return screen;
    }

    public Body getBody() {
        return body;
    }

    public boolean isHorizontalVelocityLessThan(float f) {
        return body.getLinearVelocity().x < f;
    }

    public boolean isHorizontalVelocityGreaterThan(float f) {
        return body.getLinearVelocity().x > f;
    }

    public boolean isVerticalVelocityGreaterThan(float f) {
        return body.getLinearVelocity().y > f;
    }

    public boolean isVerticalVelocityLessThan(float f) {
        return body.getLinearVelocity().y < f;
    }

    public Float getPositionX() {
        return body.getPosition().x;
    }

    public float getPositionY() {
        return body.getPosition().y;
    }

    protected void moveBody(float horizontalMagnitude, float verticalMagnitude) {
        Vector2 trajectory = new Vector2(horizontalMagnitude, verticalMagnitude);
        body.applyLinearImpulse(trajectory, body.getWorldCenter(), true);
    }

    public boolean isMovingUp() {
        return body.getLinearVelocity().y > 0;
    }

    public boolean isRunning() {
        return isMovingRight() || isMovingLeft();
    }

    public boolean isMovingLeft() {
        return body.getLinearVelocity().x < 0;
    }

    public boolean isMovingRight() {
        return body.getLinearVelocity().x > 0;
    }

    public boolean isMovingDown() {
        return body.getLinearVelocity().y < 0;
    }


    public boolean isActorInRange(Actor actor, int distanceThreshhold) {
        float delta = Math.abs(getPositionX() - actor.getPositionX());
        return delta < distanceThreshhold;
    }

    public boolean isToRight(Actor actor) {
        return actor.getPositionX() - getPositionX() > 0;
    }


    public abstract void moveRight();
    public abstract void moveLeft();
}
