package com.plusultra.puppyland.utils;

import com.badlogic.gdx.math.Vector2;
import com.plusultra.puppyland.stages.GameStage;

public class Constants {

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final float LEVEL_SPACING = GameStage.VIEWPORT_HEIGHT / 3;

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = GameStage.VIEWPORT_WIDTH * 3;
    public static final float GROUND_HEIGHT = 0.1f;
    public static final float GROUND_DENSITY = 0f;

    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 7f;
    public static final float RUNNER_X = 2f;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT + RUNNER_HEIGHT / 2;
    public static final float RUNNER_DENSITY = 0.5f;

    public static final float OBSTACLE_X = 25f;
    public static final float OBSTACLE_DENSITY = RUNNER_DENSITY;
    public static final float RUNNING_SHORT_OBSTACLE_Y = GROUND_Y + GROUND_HEIGHT;
    public static final Vector2 OBSTACLE_LINEAR_VELOCITY = new Vector2(-10f, 0);

    // Game World Dimension: 20x41
    // Screen Dimension: 2768x1440
    public static final float PIXELS_TO_METERS_X = 138.4f;
    public static final float PIXELS_TO_METERS_Y = 35f;
}
