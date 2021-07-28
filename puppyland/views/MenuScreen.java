package com.plusultra.puppyland.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.stages.MenuStage;

public class MenuScreen implements Screen{

    private TextureRegion backgroundTexture;
    private SpriteBatch batch;

    public MenuScreen() {
    }

    @Override
    public void show() {
        if(GameController.notTesting()) {
            batch = new SpriteBatch();

            backgroundTexture = new TextureRegion(new Texture("backgrounds/beach.png"),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Gdx.input.setInputProcessor(MenuStage.getMenuStage());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        MenuStage.getMenuStage().act(delta);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        MenuStage.getMenuStage().draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}