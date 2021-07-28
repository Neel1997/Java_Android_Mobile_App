package com.plusultra.puppyland.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.plusultra.puppyland.box2d.UserData;

abstract class GameActor extends Actor {

    final Body body;
    final UserData userData;

    GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    public abstract UserData getUserData();


}