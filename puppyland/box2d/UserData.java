package com.plusultra.puppyland.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.types.UserDataType;

public abstract class UserData {

    UserDataType userDataType;

    private float width;
    private float height;
    private Sprite sprite;

    UserData() {

    }

    UserData(float width, float height, String imageAsset) {
        this.width = width;
        this.height = height;
        if(GameController.notTesting())
            this.sprite = new Sprite(new Texture(Gdx.files.internal(imageAsset)));
    }

    public float getWidth() {
        return width;
    }

    public float getHeight(){
        return height;
    }

    public Sprite getSprite(){return sprite; }

    public UserDataType getUserDataType() {
        return userDataType;
    }

}
