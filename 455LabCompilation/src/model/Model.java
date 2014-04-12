package model;

import camera.Camera;
import model.handlers.*;
import model.renderers.ShapeRenderer;

public class Model {
    // Variables for creating the playing field
    private static final int TILE_SIZE = 25;
    private static final int NUM_TILES_IN_ONE_DIRECTION = 10;
    public static final int MIN_MAP_COORDINATE = -TILE_SIZE * NUM_TILES_IN_ONE_DIRECTION;
    public static final int MAX_MAP_COORDINATE = TILE_SIZE * NUM_TILES_IN_ONE_DIRECTION;

    private Camera camera;
    private ScoreHandler scoreHandler;
    private EnemyHandler enemyHandler;
    private ObstacleHandler obstacleHandler;
    private ShotsHandler shotsHandler;
    private HudHandler hudHandler;

    public Model(Camera camera, int mapNumber) {
        this.camera = camera;
        scoreHandler = new ScoreHandler();
        enemyHandler = new EnemyHandler(scoreHandler);
        obstacleHandler = new ObstacleHandler(mapNumber);
        shotsHandler = new ShotsHandler(obstacleHandler, enemyHandler);
        hudHandler = new HudHandler(scoreHandler, shotsHandler, enemyHandler);
    }

    public void update() {
        shotsHandler.updateShots();
        enemyHandler.updateEnemies(camera);
    }

    public void drawMap() {
        ShapeRenderer.drawFloorTiles(TILE_SIZE, NUM_TILES_IN_ONE_DIRECTION); // Makes floor pink
        ShapeRenderer.drawWalls(500.0, 100.0, Colors.BLUE);

        obstacleHandler.drawObstacles();
        enemyHandler.drawEnemies();
        shotsHandler.drawShots();
        hudHandler.drawHud();
    }

    public void addShot() {
        shotsHandler.addShot(camera);
    }
}
