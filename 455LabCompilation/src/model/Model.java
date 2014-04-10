package model;

import camera.Camera;
import game.Shot;
import model.mapObjects.MapCreator;
import model.mapObjects.ModelRenderer;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.MapObject;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public class Model {
    // Variables for creating the playing field
    private int tileSize = 25;
    private int numTilesInOneDirection = 10;
    private int lineWidth = 1;

    // Shots taken
    private ArrayList<Shot> shots;
    private int maxShots = 30;

    private MapCreator mapCreator;

    // Map Obstacles
    private ArrayList<MapObject> obstacles;

    // Map Enemies
    private ArrayList<EnemyEntity> enemies;

    private Vector3f shotColor = Colors.PINK;

    public Model(int mapNumber) {
        mapCreator = new MapCreator();
        shots = new ArrayList<Shot>();
        obstacles = mapCreator.createMap(mapNumber);
    }

    public void update() {
        updateShotPositions();
    }

    public void drawMap() {
        glLineWidth(lineWidth);

        ModelRenderer.drawFloor(tileSize, numTilesInOneDirection);
//        ModelRenderer.drawFloorTiles(tileSize, numTilesInOneDirection); // Makes floor pink
        ModelRenderer.drawWalls(500.0, 100.0, Colors.BLUE);

        drawObstacles();
        drawShots();
        drawShotsRemaining(Colors.WHITE);
    }

    public void drawObstacles() {
        for(MapObject obstacle : obstacles) {
            obstacle.render();
        }
    }

    public void addShot(Camera camera) {
        if (shots.size() < maxShots) {
            shots.add(new Shot(camera));
        }
    }

    public void updateShotPositions() {
        Set<Shot> shotRemoval = new HashSet<Shot>();
        for (Shot shot : shots) {
//            shot.z -= 1;
            shot.updatePosition();
            if (shot.isOutsideGameField(-tileSize * numTilesInOneDirection, tileSize * numTilesInOneDirection)) {
                shotRemoval.add(shot);
            }
        }
        for (Shot shot : shotRemoval) {
            shots.remove(shot);
        }
    }

    public void drawShots() {
        for (Shot shot : shots) {
            ModelRenderer.drawSphere(shot.size, shot.x, shot.y, shot.z, shot.slices, shot.stacks, shotColor);
        }
    }

    /**
     * Draw shot count on all the walls
     */
    public void drawShotsRemaining(Vector3f color) {
        glColor3f(color.x, color.y, color.z);
//        glRotated(-camera.rotateAngle, 0, 1, 0);
//        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), camera.xPos -102, camera.yPos + 64, camera.zPos - 93);
        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);

        glRotatef(90, 0f, 1.0f, 0f);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);

        glRotatef(180, 0f, 1.0f, 0f);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);

        glRotatef(-90, 0f, 1.0f, 0f);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    // Collision Detection Help - http://nehe.gamedev.net/tutorial/collision_detection/17005/
    // TODO: Collisions
    // 1 - rotate back
    // 2 - translate back
    // 3 - tileSize back
    // 4 - test if within the bounding box?
    // 5 - test if within the cube or within the radius - not sure how to do pyramid.
}
