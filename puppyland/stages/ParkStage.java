package com.plusultra.puppyland.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.plusultra.puppyland.GameController;
import com.plusultra.puppyland.types.ViewType;

import org.mockito.Mockito;

public class ParkStage extends Stage{

    private final OrthographicCamera cam;
    private final Skin skin;
    private TextButton buttonBack;

    private TextButton buttonPark;
    private TextButton buttonBeach;
    private TextButton buttonCity;


    private final int spriteHeight = 260;

    private TextureRegion background;
    private String testingString = "Park";

    private static ParkStage parkStage;
    private static GameController game;

    public static ParkStage getParkStage(){
        return getParkStage(false);
    }

    private static ParkStage getParkStage(boolean refresh){
        if(parkStage == null || refresh){
            if(GameController.notTesting())
                parkStage = new ParkStage();
            else
                parkStage = new ParkStage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), Mockito.mock(SpriteBatch.class));
        }
        return parkStage;
    }

    public static ParkStage getParkStage(GameController g){
        game = g;
        return getParkStage(true);
    }

    private ParkStage(){
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));
        addButtons();
    }

    private ParkStage(Viewport viewport, Batch batch){
        super(viewport, batch);

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("../android/assets/rainbow/skin/rainbow-ui.json"));
        addButtons();

        buttonPark.setProgrammaticChangeEvents(true);
        buttonBeach.setProgrammaticChangeEvents(true);
        buttonCity.setProgrammaticChangeEvents(true);

    }

    private void addButtons() {
        buttonBack = new TextButton("Back", skin);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.MENU); // change to game screen
            }
        });

        buttonPark = new TextButton("Park", skin);
        buttonPark.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameController.notTesting()) {
                    background = new TextureRegion(new Texture("backgrounds/park.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // change to game screen
                }
                else
                {
                    testingString = "Park";
                }
            }
        });

        buttonBeach = new TextButton("Beach", skin);
        buttonBeach.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameController.notTesting()) {
                    background = new TextureRegion(new Texture("backgrounds/beach.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // change to game screen
                }
                else
                {
                    testingString = "Beach";
                }
            }
        });

        buttonCity = new TextButton("City", skin);
        buttonCity.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameController.notTesting()) {
                    background = new TextureRegion(new Texture("backgrounds/city.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // change to game screen
                }
                else
                {
                    testingString = "City";
                }
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(buttonBack).height(spriteHeight/5);
        buttonTable.setBounds(0, 0, cam.viewportWidth, cam.viewportHeight);
        buttonTable.top().left();

        buttonTable.add(buttonPark).height(spriteHeight/5);
        buttonTable.setBounds(0, 0, cam.viewportWidth, cam.viewportHeight);
        buttonTable.top().left();

        buttonTable.add(buttonBeach).height(spriteHeight/5);
        buttonTable.setBounds(0, 0, cam.viewportWidth, cam.viewportHeight);
        buttonTable.top().left();

        buttonTable.add(buttonCity).height(spriteHeight/5);
        buttonTable.setBounds(0, 0, cam.viewportWidth, cam.viewportHeight);
        buttonTable.top().left();

        addActor(buttonTable);
    }

    public TextureRegion getBackground(){
        if(background == null)
        {
            return new TextureRegion(new Texture("backgrounds/park.png"),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        else {
            return background;
        }
    }

    public String getBackgroundName(){
        return testingString;
    }

    public void clickParkButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonPark.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonPark.fire(event2);
    }

    public void clickBeachButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonBeach.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonBeach.fire(event2);
    }

    public void clickCityButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonCity.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonCity.fire(event2);
    }

}
