package com.myproject.game.Tools.Factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.myproject.game.Tools.WorldContactListener;

/**
 * Created by leon on 5/1/17.
 */

public class WorldCreator {

    public static World createWorld(float x, float y) {
        World world = new World(new Vector2(x,y), true);
        world.setContactListener(new WorldContactListener());
        return world;
    }
}
