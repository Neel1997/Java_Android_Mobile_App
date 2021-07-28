package com.plusultra.puppyland.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import java.util.Locale;

public class MenuStage extends Stage{

    private final Skin skin;
    private Table table;
    private TextButton buttonPlay;
    private TextButton buttonInventory;
    private TextButton buttonPark;
    private final BitmapFont title;
    private Label highScoreLabel;

    private int highScore;

    private static MenuStage menuStage;
    private static GameController game;

    public static MenuStage getMenuStage(){
        return getMenuStage(false);
    }

    private static MenuStage getMenuStage(boolean refresh){
        if(menuStage == null || refresh){
            if(GameController.notTesting())
                menuStage = new MenuStage();
            else
                menuStage = new MenuStage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), Mockito.mock(SpriteBatch.class));
        }
        return menuStage;
    }

    public static MenuStage getMenuStage(GameController g){
        game = g;
        return getMenuStage(true);
    }

    private MenuStage(){
        skin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));
        title = new BitmapFont(Gdx.files.internal("rainbow/raw/font-title-export.fnt"));
        init();
        initHighScore();
    }

    private MenuStage(Viewport viewport, Batch batch){
        super(viewport, batch);

        skin = new Skin(Gdx.files.internal("../android/assets/rainbow/skin/rainbow-ui.json"));
        title = new BitmapFont(Gdx.files.internal("../android/assets/rainbow/raw/font-title-export.fnt"));
        init();
        initHighScore();
        buttonPlay.setProgrammaticChangeEvents(true);
        buttonInventory.setProgrammaticChangeEvents(true);
        buttonPark.setProgrammaticChangeEvents(true);
    }

    private void init(){
        highScore = 0;
        initButtons();
        table = new Table(skin);

        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label.LabelStyle headingStyle = new Label.LabelStyle(title, new Color(0, 0, 0, 1));

        Label heading = new Label("Befriend All Dogs", headingStyle);

        table.add(heading);
        table.getCell(heading).spaceBottom(180);
        table.row();

        table.add(highScoreLabel);
        table.getCell(highScoreLabel).spaceBottom(20);
        table.row();

        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(20);
        table.row();

        table.add(buttonInventory);
        table.getCell(buttonInventory).spaceBottom(20);
        table.row();

        table.add(buttonPark);
        table.getCell(buttonPark).spaceBottom(20);

        addActor(table);
    }

    private void initButtons(){
        buttonPlay = new TextButton("Play", skin);
        buttonPlay.pad(20);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.GAME);
            }
        });

        buttonInventory = new TextButton("Inventory", skin);
        buttonInventory.pad(20);
        buttonInventory.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.COLLECTION);
            }
        });

        buttonPark = new TextButton("Dog Park", skin);
        buttonPark.pad(20);
        buttonPark.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.PARK);
            }
        });
    }

    private void initHighScore(){
        highScoreLabel = new Label(String.format(Locale.US, "High Score : %02d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        highScoreLabel.setFontScale(5);

        table.add(highScoreLabel);
    }

    public void updateHighScore(int newScore){
        if(newScore > highScore)
            highScore = newScore;
        highScoreLabel.setText(String.format(Locale.US, "High Score : %02d", highScore));
    }

    public int getHighScore(){
        return highScore;
    }

    public String getHighScoreLabel(){
        return highScoreLabel.toString();
    }

    public void clickPlayButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonPlay.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonPlay.fire(event2);
    }

    public void clickCollectionButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonInventory.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonInventory.fire(event2);
    }

    public void clickParkButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonPark.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonPark.fire(event2);
    }
}
