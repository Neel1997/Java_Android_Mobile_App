package com.plusultra.puppyland.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
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
import com.plusultra.puppyland.types.DogType;
import com.plusultra.puppyland.types.ObstacleType;
import com.plusultra.puppyland.types.ViewType;

import org.mockito.Mockito;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CollectionStage extends Stage{

    private Label dogTreatLabel;

    private Vector3 dragPrevious;

    public static final int MAX_TREATS = 9999;

    private static final int VISIBLE_ROWS = 5;
    private static final float LAYOUT_PADDING_TOP = 0.5f;

    private final int numColumns = 6;
    private int numRows = 25;
    private final int spriteWidth = 454;
    private final int spriteHeight = 383;

    private TextButton buttonBack;
    private TextButton buttonShowAllDogs;
    private final FitViewport viewport;
    private final OrthographicCamera cam;
    private SpriteBatch sprites;

    private Map<ObstacleType, DogType> obstacleToDog;
    private Set<DogType> dogCollection;

    private boolean showAllDogs;

    private int numTreats;

    private static CollectionStage collectionStage;
    private static GameController game;

    public static CollectionStage getCollectionStage(){
        return getCollectionStage(false);
    }

    private static CollectionStage getCollectionStage(boolean refresh){
        if(collectionStage == null || refresh){
            if(GameController.notTesting())
                collectionStage = new CollectionStage();
            else
                collectionStage = new CollectionStage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), Mockito.mock(SpriteBatch.class));
        }
        return collectionStage;
    }

    public static CollectionStage getCollectionStage(GameController g){
        game = g;
        return getCollectionStage(true);
    }

    private CollectionStage(){
        showAllDogs = false;
        dragPrevious = new Vector3();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        double worldHeight = (numRows) * spriteHeight;
        int worldWidth = numColumns * spriteWidth;
        viewport = new FitViewport(Math.min(worldWidth, Gdx.graphics.getWidth()), (int)(worldHeight), cam);

        resetCameraPosition();
        sprites = new SpriteBatch();
        addButtons(new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json")));
        clampCamera();

        setUpDogs();
        updateDogs();

        numTreats = 0;
    }

    private CollectionStage(Viewport viewport, Batch batch){
        super(viewport, batch);
        showAllDogs = false;
        dragPrevious = new Vector3();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.viewport = (FitViewport)viewport;

        resetCameraPosition();
        addButtons(new Skin(Gdx.files.internal("../android/assets/rainbow/skin/rainbow-ui.json")));
        buttonBack.setProgrammaticChangeEvents(true);
        clampCamera();

        setUpDogs();
        updateDogs();

        numTreats = 0;
    }

    private void addButtons(Skin skin){
        buttonBack = new TextButton("Back", skin);
        buttonBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ViewType.MENU);
            }
        });
        buttonShowAllDogs = new TextButton("All Dogs", skin);
        buttonShowAllDogs.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAllDogs = !showAllDogs;
            }
        });

        dogTreatLabel = new Label(String.format(Locale.US, "Dog Treats : %02d", this.getNumTreats()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dogTreatLabel.setFontScale(5);

        Table buttonTable = new Table();
        buttonTable.add(buttonBack).height(spriteHeight/2);
        buttonTable.add(buttonShowAllDogs).height(spriteHeight/2);

        buttonTable.add(dogTreatLabel).height(spriteHeight/2);

        buttonTable.setBounds(0, 0, cam.viewportWidth, cam.viewportHeight);


        buttonTable.top().left();
        addActor(buttonTable);
    }

    private void setUpDogs(){
        obstacleToDog = new HashMap<ObstacleType, DogType>();
        obstacleToDog.put(ObstacleType.CHOW, DogType.CHOW);
        obstacleToDog.put(ObstacleType.GOLDEN, DogType.GOLDEN);
        obstacleToDog.put(ObstacleType.POODLE, DogType.POODLE);
        obstacleToDog.put(ObstacleType.SHIBA, DogType.SHIBA);
        obstacleToDog.put(ObstacleType.WEENIE, DogType.WEENIE);
        dogCollection = new HashSet<DogType>();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        super.touchDown(x, y, pointer, button);
        dragPrevious = new Vector3(x, y, 0);
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        moveCamera(x - dragPrevious.x, y - dragPrevious.y);
        clampCamera();
        dragPrevious.set(x, y, 0);
        return true;
    }

    private void moveCamera(float x, float y){
        cam.translate(x, y);
    }

    public void resetCameraPosition(){
        cam.position.set(0, viewport.getWorldHeight(), 0);
        clampCamera();
    }

    private void clampCamera() {
        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, viewport.getWorldWidth()/cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, viewport.getWorldWidth() - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, viewport.getWorldHeight() - effectiveViewportHeight / 2f);
    }

    private void updateDogs(){
//        numRows = dogCollection.size() / numColumns;
        numRows = 150 / numColumns;
    }

    @Override
    public void act(float delta){
        updateDogs();
        updateTreatDisplay();
    }

    private void updateTreatDisplay(){
        dogTreatLabel.setText(String.format(Locale.US,"Dog Treats : %02d", this.getNumTreats()));   // updates score counter
    }

    @Override
    public void draw() {
        cam.update();
        sprites.setProjectionMatrix(cam.combined);
        sprites.begin();
        Color c = sprites.getColor();
        sprites.setColor(c.r, c.g, c.b, 1f);

        int spriteWidth = (int)(cam.viewportWidth / numColumns);
        int spriteHeight = (int)(viewport.getWorldHeight() / (numRows + 0.5));
        int spriteSize = Math.min(spriteWidth, spriteHeight);

        cam.viewportWidth = Gdx.graphics.getWidth();
        cam.viewportHeight = spriteSize * VISIBLE_ROWS;

        // TODO if show all dogs is true show all else show from collection
        //List<DogType> allDogs = new ArrayList<DogType>(Arrays.asList(DogType.values()));
        int i = 0;
        for(DogType d : dogCollection) {
            sprites.draw(d.collectionTexture(),
                    spriteSize * (i % numColumns),
                    viewport.getWorldHeight() - spriteSize * ((i + numColumns) / numColumns) - (spriteSize * LAYOUT_PADDING_TOP));
            i++;
        }
            //TODO if I do not own dog, put gray it out
//            if(!dogCollection.contains(allDogs.get(i))){
//                // change to draw more transparent
//                sprites.setColor(c.r, c.g, c.b, 0.3f);
//                // draw box
//
//                // change to back opaque
//                sprites.setColor(c.r, c.g, c.b, 1f);
//            }
        sprites.end();
        super.draw();
    }

    @Override
    public void dispose(){
        sprites.dispose();
    }

    public void clickBackButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonBack.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonBack.fire(event2);
    }

    public void clickAllDogButton(){
        InputEvent event1 = new InputEvent();
        event1.setType(InputEvent.Type.touchDown);
        buttonShowAllDogs.fire(event1);

        InputEvent event2 = new InputEvent();
        event2.setType(InputEvent.Type.touchUp);
        buttonShowAllDogs.fire(event2);
    }

    public boolean isShowALlDogs(){
        return showAllDogs;
    }

    public int getNumTreats(){ return numTreats; }

    public void addTreats(int toAdd){
        if(numTreats + toAdd < MAX_TREATS)
            numTreats += toAdd;
        else
            numTreats = MAX_TREATS;
    }

    public void removeTreats(int toDec){
        numTreats -= toDec;
    }

    public void addDog(ObstacleType dog){
        if(obstacleToDog.containsKey(dog))
            dogCollection.add(obstacleToDog.get(dog));
    }

    public Set<DogType> getDogCollection(){
        return dogCollection;
    }
}
