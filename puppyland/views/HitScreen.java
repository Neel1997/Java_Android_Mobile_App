package com.plusultra.puppyland.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.stages.HitStage;

public class HitScreen implements Screen{

    private final GameController game;

    public HitScreen(GameController game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HitStage.getHitStage().act(delta);
        HitStage.getHitStage().draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        if(GameController.notTesting()){
            Gdx.input.setInputProcessor(HitStage.getHitStage(game));
        }
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

    }

}