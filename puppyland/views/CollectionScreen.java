package com.plusultra.puppyland.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.plusultra.puppyland.stages.CollectionStage;

public class CollectionScreen implements Screen {


    public CollectionScreen(){

    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(CollectionStage.getCollectionStage());
        CollectionStage.getCollectionStage().resetCameraPosition();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CollectionStage.getCollectionStage().act(delta);
        CollectionStage.getCollectionStage().draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {

    }
}
