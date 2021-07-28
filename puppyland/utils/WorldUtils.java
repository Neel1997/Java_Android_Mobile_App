package com.plusultra.puppyland.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.plusultra.puppyland.box2d.GroundUserData;
import com.plusultra.puppyland.box2d.ObstacleUserData;
import com.plusultra.puppyland.box2d.RunnerUserData;
import com.plusultra.puppyland.types.ObstacleType;

public class WorldUtils {

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createGround(World world, int level) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.GROUND_X , Constants.GROUND_Y + Constants.LEVEL_SPACING*level));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2, Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(new GroundUserData());
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world, int level) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y + Constants.LEVEL_SPACING*level));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT, "player/frame_0.png"));
        shape.dispose();
        return body;
    }

    public static Body createObstacle(World world) {
        ObstacleType obstacleType = RandomUtils.getRandomObstacleType();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(new Vector2(obstacleType.getX(), obstacleType.getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(obstacleType.getWidth() / 2, obstacleType.getHeight() / 2);

        Body body = world.createBody(bodyDef);

        body.createFixture(shape, obstacleType.getDensity());

        body.resetMassData();
        ObstacleUserData userData = new ObstacleUserData(obstacleType.getWidth(), obstacleType.getHeight(), obstacleType, obstacleType.getImageAsset());
        body.setUserData(userData);
        shape.dispose();
        return body;
    }
}
