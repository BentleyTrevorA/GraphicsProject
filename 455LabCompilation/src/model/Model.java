package model;

import camera.Camera;
import model.handlers.EnemyHandler;
import model.handlers.HudHandler;
import model.handlers.ScoreHandler;
import model.handlers.ShotsHandler;
import model.mapObjects.MapCreator;
import model.mapObjects.destructible.CubeEnemy;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.SphereEnemy;
import model.renderers.ShapeRenderer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Model {
    private int lineWidth = 1;

    // Variables for creating the playing field
    private static final int TILE_SIZE = 25;
    private static final int NUM_TILES_IN_ONE_DIRECTION = 10;
    public static final int MIN_MAP_COORDINATE = -TILE_SIZE * NUM_TILES_IN_ONE_DIRECTION;
    public static final int MAX_MAP_COORDINATE = TILE_SIZE * NUM_TILES_IN_ONE_DIRECTION;

    private MapCreator mapCreator;
    private ArrayList<MapObject> obstacles;

    private ScoreHandler scoreHandler;
    private EnemyHandler enemyHandler;
    private ShotsHandler shotsHandler;
    private HudHandler hudHandler;

    public Model(int mapNumber) {
        mapCreator = new MapCreator();

        scoreHandler = new ScoreHandler();
        enemyHandler = new EnemyHandler(scoreHandler);
        shotsHandler = new ShotsHandler(enemyHandler);
        hudHandler = new HudHandler(scoreHandler, shotsHandler, enemyHandler);

        obstacles = mapCreator.createMap(mapNumber);
    }

    public void update() {
        shotsHandler.updateShots(obstacles);
    }

    public void drawMap() {
        glLineWidth(lineWidth);

        ShapeRenderer.drawFloor(TILE_SIZE, NUM_TILES_IN_ONE_DIRECTION);
//        ShapeRenderer.drawFloorTiles(TILE_SIZE, NUM_TILES_IN_ONE_DIRECTION); // Makes floor pink
        ShapeRenderer.drawWalls(500.0, 100.0, Colors.BLUE);

        drawObstacles();
        enemyHandler.drawEnemies();
        shotsHandler.drawShots();
        hudHandler.drawHud();
    }

    public void addShot(Camera camera) {
        shotsHandler.addShot(camera);
    }

    /* **********************************************
     *                  OBSTACLES
     * *********************************************/
    public void drawObstacles() {
        for(MapObject obstacle : obstacles) {
            obstacle.render();
        }
    }
}
