package com.plusultra.puppyland.box2d;

import com.plusultra.puppyland.types.UserDataType;

public class RunnerUserData extends UserData {

    public RunnerUserData(float width, float height, String imageAsset) {
        super(width, height, imageAsset);
        userDataType = UserDataType.RUNNER;
    }
}
