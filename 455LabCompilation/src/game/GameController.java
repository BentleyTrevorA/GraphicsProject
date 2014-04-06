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
import model.HouseModel;
import model.Line3D;
import model.WireFrame;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Iterator;

import static org.lwjgl.opengl.GL11.*;import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GameController extends LabController
{
    private double xPos = 0;
    private double yPos = -2;
    private double zPos = -20;
    private double rotateAngle = 0;

    // This is a model of a house.
    private WireFrame model = new HouseModel();

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

    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    public void updateKeyboard() {

        // MOVE FORWARD
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            zPos += Math.cos(Math.toRadians(rotateAngle));
            xPos -= Math.sin(Math.toRadians(rotateAngle));
        }
        // MOVE BACKWARD
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            zPos -= Math.cos(Math.toRadians(rotateAngle));
            xPos += Math.sin(Math.toRadians(rotateAngle));
        }

        // MOVE LEFT
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            xPos += Math.cos(Math.toRadians(rotateAngle));
            zPos += Math.sin(Math.toRadians(rotateAngle));
        }
        // MOVE RIGHT
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            xPos -= Math.cos(Math.toRadians(rotateAngle));
            zPos -= Math.sin(Math.toRadians(rotateAngle));
        }

        /* MOVEMENT NOT ALLOWED, BUT WILL NEED THIS EVENTUALLY FOR MOVING UP STUFF
        // MOVE UP
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            yPos -= 1;
            printVariables();
        }
        // MOVE DOWN
        else if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            yPos += 1;
            printVariables();
        }*/

        // TURN LEFT
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            rotateAngle -= 1;
        }
        // TURN RIGHT
        else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            rotateAngle += 1;
        }

        // SWITCH TO PERSPECTIVE PROJECTION
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluPerspective(80.0f, 640 / 480f, 0f, 50f);
        }
        // SWITCH TO ORTHOGRAPHIC PROJECTION
        else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Display.getDisplayMode().getWidth()/40, 0, Display.getDisplayMode().getHeight()/40, -500, 500);
        }

        // RESET TO CENTER
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            xPos = 0;
            yPos = -2;
            zPos = -20;
            rotateAngle = 0;
        }
    }

    //This method is the one that actually draws to the screen.
    public void render() {
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT);

        // UPDATE THE VIEW
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glRotatef((float)rotateAngle, 0f, 1.0f, 0f);
        glTranslatef((float) xPos, (float) yPos, (float) zPos);

        // DRAW THE HOUSE
        glColor3f(0.0f, 1.0f, 1.0f);
        glLineWidth(2);
//        glBegin(GL_LINES);
        glBegin(GL_POLYGON);
        {
            Iterator<Line3D> iter = model.getLines();
            while (iter.hasNext()) {
                Line3D line = iter.next();
                float startX = (float) line.start.x;
                float startY = (float) line.start.y;
                float startZ = (float) line.start.z;

                float endX = (float) line.end.x;
                float endY = (float) line.end.y;
                float endZ = (float) line.end.z;

                glVertex3f(startX, startY, startZ);
                glVertex3f(endX, endY, endZ);
            }
        }
        glEnd();

        glColor3f(0, 0, 1);
        glBegin(GL_LINES);
        {
            Iterator<Line3D> iter = model.getLines();
            while (iter.hasNext()) {
                Line3D line = iter.next();
                float startX = (float) line.start.x;
                float startY = (float) line.start.y;
                float startZ = (float) line.start.z;

                float endX = (float) line.end.x;
                float endY = (float) line.end.y;
                float endZ = (float) line.end.z;

                glVertex3f(startX, startY, startZ);
                glVertex3f(endX, endY, endZ);
            }
        }
        glEnd();
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
