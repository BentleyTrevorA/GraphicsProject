package model.mapObjects;

import model.Colors;
import model.mapObjects.nondestructible.Cube;
import model.mapObjects.nondestructible.Pyramid;
import model.mapObjects.nondestructible.Sphere;

import java.util.ArrayList;

public class MapCreator {

    public ArrayList<MapObject> createMap(int mapNumber) {
        ArrayList<MapObject> map = new ArrayList<MapObject>();
        switch(mapNumber) {
            case 1:
                return createMap1(map);
            default:
                return null;
        }
    }

    private ArrayList<MapObject> createMap1(ArrayList<MapObject> map) {
        map.add(new Pyramid(100, 0, 0, Colors.CYAN));
        map.add(new Pyramid(-100, 0, 0, Colors.GREEN));
        map.add(new Pyramid(0, 0, 100, Colors.ORANGE));
        map.add(new Pyramid(0, 0, -100, Colors.PURPLE));

        map.add(new Cube(-50, 25, -50, Colors.YELLOW));
        map.add(new Cube(50, 0, 50, Colors.ORANGE));

        map.add(new Sphere(0, 25, 0, Colors.YELLOW));
        return map;
    }
}
