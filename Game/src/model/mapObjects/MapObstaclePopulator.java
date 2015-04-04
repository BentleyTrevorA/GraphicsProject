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
        map.add(new PyramidObstacle(100, 0, 0, Colors.CYAN, textureHandler));
        map.add(new PyramidObstacle(-100, 0, 0, Colors.GREEN, textureHandler));
        map.add(new PyramidObstacle(0, 0, 100, Colors.ORANGE, textureHandler));
        map.add(new PyramidObstacle(0, 0, -100, Colors.PURPLE, textureHandler));

        map.add(new CubeObstacle(-50, 25, -50, Colors.YELLOW, textureHandler));
        map.add(new CubeObstacle(50, 0, 50, Colors.ORANGE, textureHandler));

        map.add(new SphereObstacle(0, 25, 0, Colors.YELLOW, textureHandler));
        return map;
    }

    private Collection<MapObject> createTestCollisionMap1(Collection<MapObject> map) {
        map.add(new CubeObstacle(0, 0, -50, textureHandler));
        map.add(new CubeObstacle(50, 0, 0, textureHandler));
        map.add(new CubeObstacle(100, 0, -50, textureHandler));
        map.add(new CubeObstacle(150, 0, 0, textureHandler));
        map.add(new CubeObstacle(0, 0, 50, textureHandler));

        return map;
    }

    private Collection<MapObject> createTestCollisionMap2(Collection<MapObject> map) {
        map.add(new CubeObstacle(50, -50, 0, 0, textureHandler));
        map.add(new CubeObstacle(50, 50, 0, 0, textureHandler));
        map.add(new CubeObstacle(50, 0, 0, -50, textureHandler));
        map.add(new CubeObstacle(50, 0, 0, 50, textureHandler));

        return map;
    }
}
