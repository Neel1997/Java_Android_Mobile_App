package com.plusultra.puppyland.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.stages.ParkStage;


public class ParkScreen implements Screen{

    private final GameController game;

    private TextureRegion backgroundTexture;
    private SpriteBatch batch;

    public ParkScreen(GameController game) {
        this.game = game;
    }


    @Override
    public void show() {
        if(GameController.notTesting()) {
            batch = new SpriteBatch();

            //backgroundTexture = new TextureRegion(new Texture("backgrounds/park.png"),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            backgroundTexture = ParkStage.getParkStage().getBackground();

            Gdx.input.setInputProcessor(ParkStage.getParkStage(game));
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ParkStage.getParkStage().act(delta);

        backgroundTexture = ParkStage.getParkStage().getBackground();

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        ParkStage.getParkStage().draw();
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
        // TODO Auto-generated method stub
    }
}


