package com.myproject.game.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by leon on 4/28/17.
 */

public class BodyHandler {
    public final Body body;

    public BodyHandler(Body body) {
        this.body = body;
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

    public float getPositionX() {
        return body.getPosition().x;
    }

    public float getPositionY() {
        return body.getPosition().y;
    }

    public void moveBody(float horizontalMagnitude, float verticalMagnitude) {
        Vector2 trajectory = new Vector2(horizontalMagnitude, verticalMagnitude);
        body.applyLinearImpulse(trajectory, body.getWorldCenter(), true);
    }

}
