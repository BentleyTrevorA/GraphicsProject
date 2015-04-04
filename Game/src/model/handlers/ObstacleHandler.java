package model.handlers;

import model.mapObjects.MapName;
import model.mapObjects.MapObject;
import model.mapObjects.MapObstaclePopulator;
import model.mapObjects.destructible.Shot;

import java.util.Collection;

public class ObstacleHandler {
    private MapObstaclePopulator mapCreator;

    private Collection<MapObject> obstacles;

    public ObstacleHandler(TextureHandler textureHandler, MapName mapName) {
        mapCreator = new MapObstaclePopulator(textureHandler);
        obstacles = mapCreator.populateObstacles(mapName);
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
