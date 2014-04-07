package game;
//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your mileage may vary. Don't feel restricted by this list of imports.

import controllers.LabController;
import model.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GameController extends LabController {
    // Right handed coordinate system - These values (numeric) are inverted because of the translation matrix
    private double xPos = 0;  // X = Right
    private double yPos = 5; // Y = Up
    private double zPos = 20;  // Z = Back

    // Variables for creating the playing field
    private int scale = 25;
    private int numTiles = 10;

    // Shots taken
    private ArrayList<Shot> shots;
    private int maxShots = 30;

    private double rotateAngle = 0;

    MapPieces map = new MapPieces();

    protected void initBase() {
        shots = new ArrayList<Shot>();
    }

    // This method is called to "resize" the viewport to match the screen.
    // When you first start, have it be in perspective mode.
    public void resizeGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(80.0f, 640 / 480f, 0f, 50f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
    }

    public void update() {
        updateShotPositions();
    }

    public void updateShotPositions() {
        Set<Shot> shotRemoval = new HashSet<Shot>();
        for (Shot shot : shots) {
//            shot.z -= 1;
            shot.updatePosition();
            if(shot.isOutsizeGameField(-scale * numTiles, scale * numTiles)) {
                shotRemoval.add(shot);
            }
        }
        for(Shot shot : shotRemoval) {
            shots.remove(shot);
        }
    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    public void updateKeyboard() {
        // MOVE FORWARD
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            zPos -= Math.cos(Math.toRadians(rotateAngle));
            xPos += Math.sin(Math.toRadians(rotateAngle));
//            printVariables();
        }
        // MOVE BACKWARD
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            zPos += Math.cos(Math.toRadians(rotateAngle));
            xPos -= Math.sin(Math.toRadians(rotateAngle));
//            printVariables();
        }

        // MOVE LEFT
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            xPos -= Math.cos(Math.toRadians(rotateAngle));
            zPos -= Math.sin(Math.toRadians(rotateAngle));
//            printVariables();
        }
        // MOVE RIGHT
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            xPos += Math.cos(Math.toRadians(rotateAngle));
            zPos += Math.sin(Math.toRadians(rotateAngle));
//            printVariables();
        }

        /* MOVEMENT NOT ALLOWED, BUT WILL NEED THIS EVENTUALLY FOR MOVING UP STUFF */
        // MOVE UP
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            yPos += 1;
//            printVariables();
        }
        // MOVE DOWN
        else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            yPos -= 1;
//            printVariables();
        }/**/

        // TURN LEFT
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            rotateAngle -= 1;
        }
        // TURN RIGHT
        else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            rotateAngle += 1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if(shots.size() < maxShots) {
                System.out.println("Added shot #" + shots.size());
                double dz = -1 * Math.cos(Math.toRadians(rotateAngle));
                double dx = Math.sin(Math.toRadians(rotateAngle));
                shots.add(new Shot(xPos, yPos, zPos, dx, 0, dz));
            }
        }

        // SWITCH TO PERSPECTIVE PROJECTION
//        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
//            glMatrixMode(GL_PROJECTION);
//            glLoadIdentity();
//            gluPerspective(80.0f, 640 / 480f, 0f, 50f);
//        }
        // SWITCH TO ORTHOGRAPHIC PROJECTION
//        else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
//            glMatrixMode(GL_PROJECTION);
//            glLoadIdentity();
//            glOrtho(0, Display.getDisplayMode().getWidth()/40, 0, Display.getDisplayMode().getHeight()/40, -500, 500);
//        }

        // RESET TO CENTER
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            xPos = 0;
            yPos = 5;
            zPos = 20;
            rotateAngle = 0;
        }
    }

    //This method is the one that actually draws to the screen.
    public void render() {
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // UPDATE THE VIEW
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glRotatef((float) rotateAngle, 0f, 1.0f, 0f);
        glTranslatef((float) -xPos, (float) -yPos, (float) -zPos);

        glLineWidth(2);
        glEnable(GL_CULL_FACE);
//        glEnable(GL_DEPTH_TEST);
        Vector3f color = new Vector3f(1, 1, 0);

        map.drawFloor(scale, numTiles);
        map.drawWalls(scale, numTiles);

        map.drawPyramid(5, 0, 0, 0, color, false);
        map.drawPyramid(5, 10, 0, 0, color, false);
        map.drawPyramid(5, 10, 0, -15, color, false);

        map.drawCube(15, -50, 25, -50, color, true);
        map.drawCube(15, 50, 0, 50, new Vector3f(1, 0, 0), true);

        for (Shot shot : shots) {
            map.drawSphere(shot.size, shot.x, shot.y, shot.z, shot.slices, shot.stacks, color);
        }
        // TODO: Figure out how to make it so you can't just see through the shapes - culling perhaps?

        glColor3f(1, 1, 1);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), 0, 50, -250);
        glFlush();
    }

    private void printVariables() {
        System.out.println("X: " + xPos);
        System.out.println("Y: " + yPos);
        System.out.println("Z: " + zPos);
        System.out.println("Rotate Angle: " + rotateAngle);
        System.err.println("COS: " + Math.cos(Math.toRadians(rotateAngle)));
        System.err.println("SIN: " + Math.sin(Math.toRadians(rotateAngle)));
    }
}
