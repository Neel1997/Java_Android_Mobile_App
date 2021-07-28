package com.plusultra.puppyland.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.plusultra.puppyland.box2d.RunnerUserData;


public class Runner extends GameActor {

    private boolean hit = false;

    public Runner(Body body) {
        super(body);
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    public void hit() {
        hit = true;
    }


    public boolean isHit() {
        return hit;
    }

    public Body getBody(){
        return body;
    }
}
