package com.plusultra.puppyland.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.types.ViewType;

import org.mockito.Mockito;

public class HitStage extends Stage {

    private SpriteBatch batch;
    private TextureRegion backgroundTexture;
    private TextureAtlas atlas;
    private Skin skin;

    private static HitStage hitStage;
    private static GameController game;

    public static HitStage getHitStage(){
        return getHitStage(false);
    }

    private static HitStage getHitStage(boolean refresh){
        if(hitStage == null || refresh){
            if(GameController.notTesting())
                hitStage = new HitStage();
            else
                hitStage = new HitStage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), Mockito.mock(SpriteBatch.class));
        }
        return hitStage;
    }

    public static HitStage getHitStage(GameController g){
        game = g;
        return getHitStage(true);
    }

    private HitStage(){
        init();
    }

    private HitStage(Viewport viewport, Batch batch){
        super(viewport, batch);
    }

    private void init(){
        batch = new SpriteBatch();

        backgroundTexture = new TextureRegion(new Texture("backgrounds/beach.png"),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        atlas = new TextureAtlas("rainbow/skin/rainbow-ui.atlas");

        skin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));

        Table table = new Table(skin);

        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton buttonPlayAgain = new TextButton("Play Again", skin);
        buttonPlayAgain.pad(20);
        buttonPlayAgain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.GAME); // change to game screen
            }
        });

        TextButton buttonHit = new TextButton("Main Menu", skin);
        buttonHit.pad(20);
        buttonHit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.MENU); // change to hit screen
            }
        });

        BitmapFont title = new BitmapFont(Gdx.files.internal("rainbow/raw/font-title-export.fnt"));

        Label.LabelStyle headingStyle = new Label.LabelStyle(title, new Color(0, 0, 0, 1));

        Label heading = new Label("Game Over", headingStyle);

        table.add(heading);
        table.getCell(heading).spaceBottom(180);
        table.row();

        table.add(buttonPlayAgain);
        table.getCell(buttonPlayAgain).spaceBottom(20);
        table.row();

        table.add(buttonHit);
        table.getCell(buttonHit).spaceBottom(20);
        table.row();

        addActor(table);
    }

    @Override
    public void draw(){
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        super.draw();
    }

    @Override
    public void dispose(){
        skin.dispose();
        atlas.dispose();
        batch.dispose();
    }

}
