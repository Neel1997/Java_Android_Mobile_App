package com.plusultra.puppyland.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.plusultra.puppyland.GameController;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.plusultra.puppyland.actors.Ground;
import com.plusultra.puppyland.actors.Obstacle;
import com.plusultra.puppyland.actors.Runner;
import com.plusultra.puppyland.box2d.ObstacleUserData;
import com.plusultra.puppyland.box2d.UserData;
import com.plusultra.puppyland.types.ObstacleType;
import com.plusultra.puppyland.utils.BodyUtils;
import com.plusultra.puppyland.utils.Constants;
import com.plusultra.puppyland.utils.WorldUtils;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameStage extends Stage implements ContactListener{

    private int dogTreatCounter;
    private Label dogTreatLabel;
    private int scoreCounter;
    private int score;
    private Label scoreLabel;
    private final int SCORE_DELAY = 20;
    private final int TREAT_SCORE = 50;

    public static final int VIEWPORT_WIDTH = 20;
    public static final int VIEWPORT_HEIGHT = 41;

    private World world;
    private ArrayList<Runner> runner;
    private int activeRunner;
    private final List<Body> toRemove;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private SpriteBatch batch;

    private Rectangle screenRightSide;

    private Vector3 touchPoint;

    private static GameStage gameStage;

    public static GameStage getGameStage(){
        return getGameStage(false);
    }

    public static GameStage getGameStage(boolean refresh){
        if(gameStage == null || refresh){
            if(GameController.notTesting())
                gameStage = new GameStage();
            else
                gameStage = new GameStage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), Mockito.mock(SpriteBatch.class));
        }
        return gameStage;
    }

    private GameStage() {
        dogTreatCounter = CollectionStage.getCollectionStage().getNumTreats();
        toRemove = new ArrayList<Body>();
        batch = new SpriteBatch();
        scoreCounter = score = 0;
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
    }

    private GameStage(Viewport viewport, Batch batch){
        super(viewport, batch);
        dogTreatCounter = CollectionStage.getCollectionStage().getNumTreats();
        toRemove = new ArrayList<Body>();
        scoreCounter = score = 0;
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (((BodyUtils.bodyIsTreat(a)) && BodyUtils.bodyIsRunner(b)) || (BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsTreat(b))){
            collectTreat();  // increment score if dog treat is collected
            if(BodyUtils.bodyIsTreat(a))
                toRemove.add(a);
            else
                toRemove.add(b);
            return;
        }
        if (((BodyUtils.bodyIsDog(a)) && BodyUtils.bodyIsRunner(b)) || (BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsDog(b))){
            ObstacleType o;
            if(BodyUtils.bodyIsDog(a)) {
                o = ((ObstacleUserData) a.getUserData()).getObstacleType();
                toRemove.add(a);
            }
            else {
                o = ((ObstacleUserData) b.getUserData()).getObstacleType();
                toRemove.add(b);
            }
            collectDog(o);
            return;
        }
        if (((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsObstacle(b) && !BodyUtils.bodyIsTreat(b)) || ((BodyUtils.bodyIsObstacle(a)) && BodyUtils.bodyIsRunner(b) && !BodyUtils.bodyIsTreat(a)))) {
            runner.get(activeRunner).hit();
        }
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpGround();
        setUpRunner();
        createObstacle();
        createHud();
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            if(activeRunner != runner.size() - 1){
                activeRunner++;
            }
        } else {
            if(activeRunner != 0){
                activeRunner--;
            }
        }

        return super.touchDown(x, y, pointer, button);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    private void setUpGround() {
        ArrayList<Ground> ground = new ArrayList<Ground>();
        ground.add(new Ground(WorldUtils.createGround(world, 0)));
        ground.add(new Ground(WorldUtils.createGround(world, 1)));
        ground.add(new Ground(WorldUtils.createGround(world, 2)));
        for(Ground g : ground){
            addActor(g);
        }
    }

    private void setUpRunner() {
        activeRunner = 0;
        runner = new ArrayList<Runner>();
        runner.add(new Runner(WorldUtils.createRunner(world, 0)));
        runner.add(new Runner(WorldUtils.createRunner(world, 1)));
        runner.add(new Runner(WorldUtils.createRunner(world, 2)));
        for(Runner r : runner){
            addActor(r);
        }
    }


    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        batch.begin();

        updateScore();

        for(int i = 0; i < runner.size(); i++){
            runner.get(i).getBody().setActive(i == activeRunner);
        }
        drawRunner();

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        // Fixed time step
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
        batch.end();
    }

    private void update(Body body) {
        if((BodyUtils.bodyIsTreat(body) || BodyUtils.bodyIsDog(body)) && toRemove.contains(body)){
            toRemove.remove(body);
            createObstacle();
            world.destroyBody(body);
            return;
        }
        if (!BodyUtils.bodyInBounds(body)) {
            if (BodyUtils.bodyIsObstacle(body) && !runner.get(activeRunner).isHit()) {
                createObstacle();
            }
            world.destroyBody(body);
            return;
        }
        UserData userData = (UserData) body.getUserData();
        if(userData.getSprite() != null && !BodyUtils.bodyIsRunner(body)) {
            batch.draw(userData.getSprite(), (body.getPosition().x - userData.getWidth() / 2) * Constants.PIXELS_TO_METERS_X,
                    (body.getPosition().y - userData.getWidth() / 2) * Constants.PIXELS_TO_METERS_Y);
        }
    }

    private void drawRunner(){
        UserData userData = runner.get(getActiveRunner()).getUserData();
        Body body = runner.get(getActiveRunner()).getBody();
        Sprite sprite = userData.getSprite();
        batch.draw(sprite, (body.getPosition().x - userData.getWidth() / 2) * Constants.PIXELS_TO_METERS_X - 200,
                (body.getPosition().y - userData.getWidth() / 2) * Constants.PIXELS_TO_METERS_Y - 150);
    }

    public void createObstacle() {
        Obstacle obstacle = new Obstacle(WorldUtils.createObstacle(world));
        addActor(obstacle);
    }

    private void createHud(){  // created score counter hud
        renderer = new Box2DDebugRenderer();
        //define our tracking variables
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        dogTreatLabel = new Label(String.format(Locale.US, "Dog Treats : %02d", dogTreatCounter), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dogTreatLabel.setFontScale(5);

        scoreLabel = new Label(String.format(Locale.US, "SCORE : %02d", scoreCounter), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(5);

        table.add(dogTreatLabel);
        table.add(scoreLabel);

        //add our table to the stage
        addActor(table);
    }

    public void updateTreatDisplay(){
        dogTreatLabel.setText(String.format(Locale.US, "Dog Treats : %02d", dogTreatCounter));   // updates dog treat counter
    }


    private void updateScoreDisplay(){
        scoreLabel.setText(String.format(Locale.US, "Score : %02d", score));   // updates score counter
    }

    public Label getDogTreatLabel(){
        return dogTreatLabel;
    }

    public void updateScore(){
        if(scoreCounter == 0){
            addScore(1);
        }
        scoreCounter = (scoreCounter + 1) % SCORE_DELAY;
        MenuStage.getMenuStage().updateHighScore(score);
        updateScoreDisplay();
    }

    public void addScore(int toAdd){
        score += toAdd;
    }


    public int getScore(){ return score; }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

    public Body getObstacle(){
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        for (Body body : bodies) {
            if(BodyUtils.bodyIsObstacle(body))
                return body;
        }
        return null;
    }

    public void removeObstacle(){
        Body body = getObstacle();
        world.destroyBody(body);
    }

    public void collectTreat(){
        CollectionStage.getCollectionStage().addTreats(1);
        dogTreatCounter = CollectionStage.getCollectionStage().getNumTreats(); // keep treats synchronized
        addScore(TREAT_SCORE);
    }

    public void collectDog(ObstacleType o){
        if(dogTreatCounter < o.getCost()) {
            runner.get(activeRunner).hit();
            return;
        }
        // remove cost of dog from treats
        CollectionStage.getCollectionStage().removeTreats(o.getCost());
        dogTreatCounter -= o.getCost();
        CollectionStage.getCollectionStage().addDog(o);
    }

    public Runner getRunner(){
        return runner.get(activeRunner);
    }

    public int getActiveRunner(){
        return activeRunner;
    }

    public int getNumTreats(){
        return dogTreatCounter;
    }
}
