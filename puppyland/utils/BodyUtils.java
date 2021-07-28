package com.plusultra.puppyland.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.plusultra.puppyland.box2d.UserData;
import com.plusultra.puppyland.types.UserDataType;

public class BodyUtils {

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyInBounds(Body body) {
        UserData userData = (UserData) body.getUserData();

        switch (userData.getUserDataType()) {
            case RUNNER:
            case HAZARD:
            case TREAT:
            case DOG:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
        }

        return true;
    }

    public static boolean bodyIsObstacle(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && (userData.getUserDataType() == UserDataType.HAZARD || userData.getUserDataType() == UserDataType.TREAT || userData.getUserDataType() == UserDataType.DOG);
    }

    public static boolean bodyIsTreat(Body body){
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.TREAT;
    }

    public static boolean bodyIsDog(Body body){
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.DOG;
    }
}
