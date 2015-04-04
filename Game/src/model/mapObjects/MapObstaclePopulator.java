package model.mapObjects;

import model.Colors;
import model.handlers.TextureHandler;
import model.mapObjects.nondestructible.CubeObstacle;
import model.mapObjects.nondestructible.PyramidObstacle;
import model.mapObjects.nondestructible.SphereObstacle;

import java.util.Collection;
import java.util.HashSet;

public class MapObstaclePopulator {
    private TextureHandler textureHandler;

    public MapObstaclePopulator(TextureHandler textureHandler) {
        this.textureHandler = textureHandler;
    }

    public Collection<MapObject> populateObstacles(MapName mapName) {
        switch (mapName) {
            case EMPTY_MAP:
                return createBaseMap();
            case BASIC_MAP:
                return createMap1();
            case EXPERIMENT_MAP:
                return createMap2();
            case TEST_COLLISION_MAP1:
                return createTestCollisionMap1();
            case TEST_COLLISION_MAP2:
                return createTestCollisionMap2();
            default:
                return null;
        }
    }

    private Collection<MapObject> createBaseMap() {
        return new HashSet<>();
    }

    private Collection<MapObject> createMap1() {
        Collection<MapObject> objectsOnMap = new HashSet<>();
        objectsOnMap.add(new PyramidObstacle(100, 0, 0, Colors.CYAN, textureHandler));
        objectsOnMap.add(new PyramidObstacle(-100, 0, 0, Colors.GREEN, textureHandler));
        objectsOnMap.add(new PyramidObstacle(0, 0, 100, Colors.ORANGE, textureHandler));
        objectsOnMap.add(new PyramidObstacle(0, 0, -100, Colors.PURPLE, textureHandler));

        objectsOnMap.add(new CubeObstacle(-50, 25, -50, Colors.YELLOW, textureHandler));
        objectsOnMap.add(new CubeObstacle(50, 0, 50, Colors.ORANGE, textureHandler));

        objectsOnMap.add(new SphereObstacle(0, 25, 0, Colors.YELLOW, textureHandler));
        return objectsOnMap;
    }

    private Collection<MapObject> createMap2() {
        Collection<MapObject> objectsOnMap = new HashSet<>();
        objectsOnMap.add(new PyramidObstacle(100, 0, 0, Colors.CYAN, textureHandler));
        objectsOnMap.add(new PyramidObstacle(-100, 0, 0, Colors.GREEN, textureHandler));
        objectsOnMap.add(new PyramidObstacle(0, 0, 100, Colors.ORANGE, textureHandler));
        objectsOnMap.add(new PyramidObstacle(0, 0, -100, Colors.PURPLE, textureHandler));

        objectsOnMap.add(new CubeObstacle(-50, 25, -50, Colors.YELLOW, textureHandler));
        objectsOnMap.add(new CubeObstacle(50, 0, 50, Colors.ORANGE, textureHandler));

        objectsOnMap.add(new SphereObstacle(0, 25, 0, Colors.YELLOW, textureHandler));
        return objectsOnMap;
    }

    private Collection<MapObject> createTestCollisionMap1() {
        Collection<MapObject> objectsOnMap = new HashSet<>();
        objectsOnMap.add(new CubeObstacle(0, 0, -50, textureHandler));
        objectsOnMap.add(new CubeObstacle(50, 0, 0, textureHandler));
        objectsOnMap.add(new CubeObstacle(100, 0, -50, textureHandler));
        objectsOnMap.add(new CubeObstacle(150, 0, 0, textureHandler));
        objectsOnMap.add(new CubeObstacle(0, 0, 50, textureHandler));

        return objectsOnMap;
    }

    private Collection<MapObject> createTestCollisionMap2() {
        Collection<MapObject> objectsOnMap = new HashSet<>();
        objectsOnMap.add(new CubeObstacle(50, -50, 0, 0, textureHandler));
        objectsOnMap.add(new CubeObstacle(50, 50, 0, 0, textureHandler));
        objectsOnMap.add(new CubeObstacle(50, 0, 0, -50, textureHandler));
        objectsOnMap.add(new CubeObstacle(50, 0, 0, 50, textureHandler));

        return objectsOnMap;
    }
}
