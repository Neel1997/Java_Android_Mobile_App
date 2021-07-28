package com.plusultra.puppyland.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.actors.Runner;
import com.plusultra.puppyland.stages.GameStage;
import com.plusultra.puppyland.types.ViewType;

import java.util.Random;


public class GameScreen implements Screen {

    private final GameController game;
    private Runner runner;

    private Texture bg1, bg2, bg3;
    private SpriteBatch batch;
    private float xMax, x1, x2;
    private int rng;

    private final int BACKGROUND_MOVE_SPEED = 100;
    private final Random random = new Random();
    private final Texture[] bg = {bg1 , bg2 , bg3};

    public GameScreen(GameController game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        x1 -= BACKGROUND_MOVE_SPEED * delta;
        x2 = x1 - xMax;  // We move the background, not the camera
        if (x1 <= 0) {
            x1 = xMax*(1);
            x2 = 0;
        }

        batch.begin();
        batch.draw(bg[rng], x1,0);
        batch.draw(bg[rng], x2,0);
        batch.end();

        //Update the stage
        GameStage.getGameStage().draw();
        updateRunner();
        updateScore();
        GameStage.getGameStage().act(delta);

    }

    public boolean updateRunner(){
        runner = GameStage.getGameStage().getRunner();
        if(runner == null){
            return false;
        }
        if(runner.isHit())
        {
            game.changeScreen(ViewType.HIT);
        }
        return runner.isHit();
    }

    private void updateScore(){
        runner = GameStage.getGameStage().getRunner();
        if(runner != null)                // If runner is alive and runner has hit a dog treat then add to score
        {
            GameStage.getGameStage().updateTreatDisplay();
        }

    }

    public void hitRunner(){
        GameStage.getGameStage().getRunner().hit();
    }

    @Override
    public void show() {
        GameStage.getGameStage(true);
        if(GameController.notTesting()) {
            batch = new SpriteBatch();
            bg1 = new Texture("backgrounds/park.png");
            bg2 = new Texture("backgrounds/beach.png");
            bg3 = new Texture("backgrounds/city.png");


            bg[0] = bg1;
            bg[1] = bg2;
            bg[2] = bg3;

            rng = random.nextInt(3);

            xMax = 2960;
            x1 = xMax * (1);
            x2 = 0;
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {

    }
}

