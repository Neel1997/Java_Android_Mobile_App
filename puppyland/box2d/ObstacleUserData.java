package com.plusultra.puppyland.box2d;

import com.badlogic.gdx.math.Vector2;
import com.plusultra.puppyland.types.ObstacleType;
import com.plusultra.puppyland.types.UserDataType;
import com.plusultra.puppyland.utils.Constants;

public class ObstacleUserData extends UserData {

    private final Vector2 linearVelocity;
    private final ObstacleType type;

    public ObstacleUserData(float width, float height, ObstacleType type, String imageAsset) {
        super(width, height, imageAsset);
        this.type = type;
        switch(type){
            case DOG_TREAT:
                userDataType = UserDataType.TREAT;
                break;
            case HAZARD:
                userDataType = UserDataType.HAZARD;
                break;
            case CHOW:
            case GOLDEN:
            case POODLE:
            case SHIBA:
            case WEENIE:
                userDataType = UserDataType.DOG;
                break;
        }
        linearVelocity = Constants.OBSTACLE_LINEAR_VELOCITY;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public ObstacleType getObstacleType(){
        return type;
    }

}