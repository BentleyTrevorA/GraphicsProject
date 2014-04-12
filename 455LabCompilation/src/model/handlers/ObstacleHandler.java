package model.handlers;

import model.mapObjects.destructible.Shot;
import model.mapObjects.MapObstaclePopulator;
import model.mapObjects.MapObject;
import model.renderers.ShapeRenderer;

import java.util.Collection;

public class ObstacleHandler {
    private MapObstaclePopulator mapCreator;
    private ShapeRenderer shapeRenderer;

    private Collection<MapObject> obstacles;

    public ObstacleHandler(ShapeRenderer shapeRenderer, int mapNumber) {
        this.shapeRenderer = shapeRenderer;
        mapCreator = new MapObstaclePopulator(shapeRenderer);
        obstacles = mapCreator.createMap(mapNumber);
    }

    public void drawObstacles() {
        for (MapObject obstacle : obstacles) {
            obstacle.render();
        }
    }

    public MapObject findObstacleHitByShot(Shot shot) {
        for (MapObject obstacle : obstacles) {
            if (obstacle.isCollidingWith(shot)) {
                return obstacle;
            }
        }
        return null;
    }
}
