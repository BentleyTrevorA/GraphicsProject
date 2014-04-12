package model.mapObjects;

import model.Colors;
import model.mapObjects.nondestructible.CubeObstacle;
import model.mapObjects.nondestructible.PyramidObstacle;
import model.mapObjects.nondestructible.SphereObstacle;
import model.renderers.ShapeRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class MapObstaclePopulator {
    private ShapeRenderer shapeRenderer;

    public MapObstaclePopulator(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public Collection<MapObject> createMap(int mapNumber) {
        Collection<MapObject> map = new HashSet<MapObject>();
        switch (mapNumber) {
            case 0:
                return createBaseMap(map);
            case -1:
                return createTestCollisionMap1(map);
            case -2:
                return createTestCollisionMap2(map);
            case 1:
                return createMap1(map);
            default:
                return null;
        }
    }

    private Collection<MapObject> createBaseMap(Collection<MapObject> map) {
        return map;
    }

    private Collection<MapObject> createMap1(Collection<MapObject> map) {
        map.add(new PyramidObstacle(100, 0, 0, Colors.CYAN, shapeRenderer));
        map.add(new PyramidObstacle(-100, 0, 0, Colors.GREEN, shapeRenderer));
        map.add(new PyramidObstacle(0, 0, 100, Colors.ORANGE, shapeRenderer));
        map.add(new PyramidObstacle(0, 0, -100, Colors.PURPLE, shapeRenderer));

        map.add(new CubeObstacle(-50, 25, -50, Colors.YELLOW, shapeRenderer));
        map.add(new CubeObstacle(50, 0, 50, Colors.ORANGE, shapeRenderer));

        map.add(new SphereObstacle(0, 25, 0, Colors.YELLOW, shapeRenderer));
        return map;
    }

    private Collection<MapObject> createTestCollisionMap1(Collection<MapObject> map) {
        map.add(new CubeObstacle(0, 0, -50, shapeRenderer));
        map.add(new CubeObstacle(50, 0, 0, shapeRenderer));
        map.add(new CubeObstacle(100, 0, -50, shapeRenderer));
        map.add(new CubeObstacle(150, 0, 0, shapeRenderer));
        map.add(new CubeObstacle(0, 0, 50, shapeRenderer));

        return map;
    }

    private Collection<MapObject> createTestCollisionMap2(Collection<MapObject> map) {
        map.add(new CubeObstacle(50, -50, 0, 0, shapeRenderer));
        map.add(new CubeObstacle(50, 50, 0, 0, shapeRenderer));
        map.add(new CubeObstacle(50, 0, 0, -50, shapeRenderer));
        map.add(new CubeObstacle(50, 0, 0, 50, shapeRenderer));

        return map;
    }
}
