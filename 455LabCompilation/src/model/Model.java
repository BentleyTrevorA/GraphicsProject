package model;

import camera.Camera;
import game.Shot;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public class Model {
    // Variables for creating the playing field
    private int scale = 25;
    private int numTiles = 10;
    private int lineWidth = 1;

    // Shots taken
    private ArrayList<Shot> shots;
    private int maxShots = 30;

    private Vector3f shotColor = Colors.PINK;

    public Model() {
        shots = new ArrayList<Shot>();
    }

    public void update() {
        updateShotPositions();
    }

    public void drawMap() {
        glLineWidth(lineWidth);

        MapPieces.drawFloor(scale, numTiles);
        MapPieces.drawWalls(scale, numTiles, Colors.BLUE);

        MapPieces.drawPyramid(15, 100, 0, 0, Colors.CYAN, true);
        MapPieces.drawPyramid(15, -100, 0, 0, Colors.GREEN, true);
        MapPieces.drawPyramid(20, 0, 0, 100, Colors.LIGHT_BLUE, true);
        MapPieces.drawPyramid(25, 0, 0, -100, Colors.PURPLE, true);

        MapPieces.drawCube(15, -50, 25, -50, Colors.RED, true);
        MapPieces.drawCube(15, 50, 0, 50, Colors.ORANGE, true);

        MapPieces.drawSphere(5, 0, 25, 0, 25, 25, Colors.YELLOW);

//        MapPieces.drawSphere(5, 0, 35, 0, 25, 25, Colors.CYAN);

        drawShots();
        drawShotsRemaining(Colors.WHITE);
    }

    public void addShot(Camera camera) {
        if(shots.size() < maxShots) {
            shots.add(new Shot(camera));
        }
    }

    public void updateShotPositions() {
        Set<Shot> shotRemoval = new HashSet<Shot>();
        for (Shot shot : shots) {
//            shot.z -= 1;
            shot.updatePosition();
            if(shot.isOutsideGameField(-scale * numTiles, scale * numTiles)) {
                shotRemoval.add(shot);
            }
        }
        for(Shot shot : shotRemoval) {
            shots.remove(shot);
        }
    }

    public void drawShots() {
        for (Shot shot : shots) {
            MapPieces.drawSphere(shot.size, shot.x, shot.y, shot.z, shot.slices, shot.stacks, shotColor);
        }
    }

    /** Draw shot count on all the walls */
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
    // 3 - scale back
    // 4 - test if within the bounding box?
    // 5 - test if within the cube or within the radius - not sure how to do pyramid.
}
