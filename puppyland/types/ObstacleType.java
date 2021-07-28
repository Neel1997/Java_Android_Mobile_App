package com.plusultra.puppyland.types;

import com.plusultra.puppyland.utils.Constants;

public enum ObstacleType {

        HAZARD("obstacles/beach/poo.png"),
        DOG_TREAT("treat.png"),
        CHOW(DogType.CHOW.gameAsset(), DogType.CHOW.cost()),
        GOLDEN(DogType.GOLDEN.gameAsset(), DogType.GOLDEN.cost()),
        POODLE(DogType.POODLE.gameAsset(), DogType.POODLE.cost()),
        SHIBA(DogType.SHIBA.gameAsset(), DogType.SHIBA.cost()),
        WEENIE(DogType.WEENIE.gameAsset(), DogType.WEENIE.cost());

        private final float width;
        private final float height;
        private final float x;
        private final float y;
        private final float density;
        private int cost;
        private final String imageAsset;

        ObstacleType(String imageAsset) {
            this.width = 1f;
            this.height = 2f;
            this.x = Constants.OBSTACLE_X;
            this.y = Constants.RUNNING_SHORT_OBSTACLE_Y;
            this.density = Constants.OBSTACLE_DENSITY;
            this.imageAsset = imageAsset;
            this.cost = 0;
        }

        ObstacleType(String imageAsset, int cost) {
            this(imageAsset);
            this.cost = cost;
        }


        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getDensity() {
            return density;
        }

        public String getImageAsset(){
            return imageAsset;
        }

        public int getCost(){
            return cost;
        }
}
