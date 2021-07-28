package com.plusultra.puppyland.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.plusultra.puppyland.box2d.ObstacleUserData;

public class Obstacle extends GameActor {

    public Obstacle(Body body) {
        super(body);
    }

    @Override
    public ObstacleUserData getUserData() {
        return (ObstacleUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

}
