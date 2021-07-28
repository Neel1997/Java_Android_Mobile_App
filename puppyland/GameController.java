package com.plusultra.puppyland;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.plusultra.puppyland.stages.CollectionStage;
import com.plusultra.puppyland.stages.GameStage;
import com.plusultra.puppyland.stages.MenuStage;
import com.plusultra.puppyland.types.ViewType;
import com.plusultra.puppyland.views.CollectionScreen;
import com.plusultra.puppyland.views.GameScreen;
import com.plusultra.puppyland.views.HitScreen;
import com.plusultra.puppyland.views.LoadingScreen;
import com.plusultra.puppyland.views.MenuScreen;
import com.plusultra.puppyland.views.ParkScreen;

import java.util.HashMap;
import java.util.Map;

public class GameController extends Game {
    private static boolean isTesting;

    private final Map<ViewType, Screen> screens;
    private Screen currentScreen;

    public GameController(){
        isTesting = false;
        screens = new HashMap<ViewType, Screen>();
        screens.put(ViewType.LOADING, new LoadingScreen(this));
        screens.put(ViewType.MENU, new MenuScreen());
        screens.put(ViewType.COLLECTION, new CollectionScreen());
        screens.put(ViewType.GAME, new GameScreen(this));
        screens.put(ViewType.HIT, new HitScreen(this));
        screens.put(ViewType.PARK, new ParkScreen(this));

        currentScreen = screens.get(ViewType.LOADING);
    }

    public GameController(boolean isTest){
        this();
        isTesting = isTest;
        initStages();
    }

    private void initStages(){
        MenuStage.getMenuStage(this);
        GameStage.getGameStage(true);
        CollectionStage.getCollectionStage(this);
    }

    @Override
    public void create () {
        this.setScreen(screens.get(ViewType.LOADING));
        currentScreen = screens.get(ViewType.LOADING);
        initStages();
    }

    @Override
    public void dispose () {


    }
    public Screen changeScreen(ViewType screen){
        if(screens.containsKey(screen)) {
            this.setScreen(screens.get(screen));
            currentScreen = screens.get(screen);
        }
        return getCurrentScreen();
    }

    public Screen getCurrentScreen(){
        return currentScreen;
    }

    public static boolean notTesting(){
        return !isTesting;
    }
}



