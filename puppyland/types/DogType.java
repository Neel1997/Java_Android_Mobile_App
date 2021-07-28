package com.plusultra.puppyland.types;

import com.badlogic.gdx.graphics.Texture;
import com.plusultra.puppyland.GameController;

public enum DogType {
    CHOW("chow/chow.png", "chow/chow.png", 4),
    GOLDEN("golden/golden.png", "golden/golden.png", 1),
    POODLE("poodle/poodle.png", "poodle/poodle.png", 5),
    SHIBA("shiba/shiba.png", "shiba/shiba.png", 15),
    WEENIE("weenie/weenie.png", "weenie/weenie.png", 7);
    //... types of dogs with associated collection image, game image, and cost to collect

    private final int cost;
    private final String gameAsset;
    private final Texture collectionTexture;

    DogType(String collectionAsset, String gameAsset, int cost){
        if(GameController.notTesting())
            this.collectionTexture = new Texture(collectionAsset);
        else
            this.collectionTexture = null;
        this.gameAsset = gameAsset;
        this.cost = cost;
    }
    public int cost(){
        return cost;
    }

    public String gameAsset(){
        return gameAsset;
    }

    public Texture collectionTexture(){
        return collectionTexture;
    }
}
