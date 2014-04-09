package game;

// Collision Detection Help - http://nehe.gamedev.net/tutorial/collision_detection/17005/
// TODO: Collisions
// 1 - rotate back
// 2 - translate back
// 3 - scale back
// 4 - test if within the bounding box?
// 5 - test if within the cube or within the radius - not sure how to do pyramid.

import controllers.LabController;
import model.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashSet;
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

    public Vector3f shotColor = Colors.PINK;

    //----------- Variables added for Lighting Test -----------//
    private FloatBuffer matSpecular;
    private FloatBuffer lightPosition, lightPosition2;
    private FloatBuffer whiteLight, pinkLight;
    private FloatBuffer lModelAmbient;
    //----------- END: Variables added for Lighting Test -----------//

    // Lighting Position
    float[] position = {0, 3, -10, 1};
    float a = .1f;
    float b = .1f;
    float c = .1f;
    float d = 0;

    // Shots taken
    private ArrayList<Shot> shots;
    private int maxShots = 30;

    private double rotateAngle = 0;

    MapPieces map = new MapPieces();

    protected void initBase() {
        shots = new ArrayList<Shot>();
        initLightArrays();
    }

    // This method is called to "resize" the viewport to match the screen.
    // When you first start, have it be in perspective mode.
    public void resizeGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        // IMPORTANT: DO NOT use 0 for near plane - it screws up depth_testing
        gluPerspective(80.0f, 960 / 720f, 1.0f, 700f);

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
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            xPos -= Math.cos(Math.toRadians(rotateAngle));
            zPos -= Math.sin(Math.toRadians(rotateAngle));
//            printVariables();
        }
        // MOVE RIGHT
        else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            xPos += Math.cos(Math.toRadians(rotateAngle));
            zPos += Math.sin(Math.toRadians(rotateAngle));
//            printVariables();
        }

        // TURN LEFT
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            rotateAngle -= 1;
        }
        // TURN RIGHT
        else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            rotateAngle += 1;
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
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_P)) { d += (1.0/180.f); printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_SEMICOLON)) { d -= (1.0/180.f); printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) { c += .1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) { c -= .1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) { b += .1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) { b -= .1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_U)) { a += .1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) { a -= .1f; printLightVariables();}

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            addShot();
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
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // | GL_LIGHTING_BIT);

        // UPDATE THE VIEW
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glRotated(rotateAngle, 0f, 1.0f, 0f);
        glTranslated(-xPos, -yPos, -zPos);

        enableDepthBuffering();
//        enableLighting2();
        enableLighting();

        glLineWidth(2);
        glEnable(GL_CULL_FACE);

        map.drawFloor(scale, numTiles);
        map.drawWalls(scale, numTiles, Colors.BLUE);

        map.drawPyramid(15, 100, 0, 0, Colors.CYAN, true);
        map.drawPyramid(15, -100, 0, 0, Colors.GREEN, false);
        map.drawPyramid(20, 0, 0, 100, Colors.LIGHT_BLUE, false);
        map.drawPyramid(25, 0, 0, -100, Colors.PURPLE, false);

        map.drawCube(15, -50, 25, -50, Colors.RED, true);
        map.drawCube(15, 50, 0, 50, Colors.ORANGE, false);

        map.drawSphere(5, 0, 25, 0, 25, 25, Colors.YELLOW);

//        map.drawSphere(5, 0, 35, 0, 25, 25, Colors.CYAN);
        for (Shot shot : shots) {
            map.drawSphere(shot.size, shot.x, shot.y, shot.z, shot.slices, shot.stacks, shotColor);
        }

        drawShotsRemaining(Colors.WHITE);

        // TODO: Figure out how to make it so you can't just see through the shapes - culling perhaps?

        glFlush();
    }

    private void addShot() {
        if(shots.size() < maxShots) {
            // Put shots out in front of the player
            double z = zPos - 4 * Math.cos(Math.toRadians(rotateAngle));
            double x = xPos + 4 * Math.sin(Math.toRadians(rotateAngle));

            // Set speed of ball to be faster than the player so if they are running it's not weird
            double dz = -2 * Math.cos(Math.toRadians(rotateAngle));
            double dx = 2 * Math.sin(Math.toRadians(rotateAngle));
            shots.add(new Shot(x, yPos, z, dx, 0, dz));
        }
    }

    private void enableDepthBuffering() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // sets background to black
        glClearDepth(1.0f); // clear depth buffer
        glEnable(GL_DEPTH_TEST); // Enables depth testing
//        glDepthFunc(GL_LEQUAL); // sets the type of test to use for depth testing
        glDepthFunc(GL_LEQUAL); // sets the type of test to use for depth testing
        glDepthRange(0, 1);

        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    private void enableLighting2() {
//        initLightArrays();
        glPushMatrix();
//        glTranslated(50, 0, 0);
        glShadeModel(GL_SMOOTH);
        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);				// sets specular material color
        glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess

        glLight(GL_LIGHT0, GL_POSITION, lightPosition);				// sets light position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);				// sets specular light to white
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);					// sets diffuse light to white

        glLight(GL_LIGHT1, GL_POSITION, lightPosition2);			// sets light position
        glLight(GL_LIGHT1, GL_SPECULAR, whiteLight);				// sets specular light to white
        glLight(GL_LIGHT1, GL_DIFFUSE, pinkLight);					// sets diffuse light to pink

        glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light

//        glEnable(GL_NORMALIZE);
        glEnable(GL_LIGHTING);										// enables lighting
        glEnable(GL_LIGHT0);										// enables light0
        glEnable(GL_LIGHT1);										// enables light1

        glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
        glPopMatrix();
    }

    private void initLightArrays() {
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

        lightPosition2 = BufferUtils.createFloatBuffer(4);
        lightPosition2.put(-62).put(123).put(-47).put(1.0f).flip();

        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        pinkLight = BufferUtils.createFloatBuffer(4);
        pinkLight.put(.5f).put(0).put(.5f).put(1).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
    }

    // LIGHTING QUESTIONS: http://www.thejavahub.net/thejavahub/index.php?topic=2124.0
    private void enableLighting() {
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * 4);
        temp.order(ByteOrder.LITTLE_ENDIAN);

        glEnable(GL_NORMALIZE);
        glEnable(GL_LIGHTING);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_LIGHT0);
        float[] diffuse_color = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
//        float[] ambient_color = {0.0f, 0.5f, 0.5f, 1};
//        float[] position = {0, 3, -10, 1};
        float[] position = {25, 38, -66, .1999f};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(diffuse_color).flip());
        glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(ambient_color).flip());
        glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(position).flip());

        glEnable(GL_LIGHT1);
        float[] diffuse_color1 = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color1 = {0.5f, 0.0f, 0.5f, 1};
//        float[] ambient_color1 = {1f, 0.0f, 0.0f, 1};
        float[] position1 = {a, b, c, d};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        glLight(GL_LIGHT1, GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(diffuse_color1).flip());
        glLight(GL_LIGHT1, GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(ambient_color1).flip());
        glLight(GL_LIGHT1, GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(position1).flip());
    }

    private void drawShotsRemaining(Vector3f color) {
        glColor3f(color.x, color.y, color.z);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);

        glRotatef(90, 0f, 1.0f, 0f);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);

        glRotatef(180, 0f, 1.0f, 0f);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);

        glRotatef(-90, 0f, 1.0f, 0f);
        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), -50, 50, -250);
    }

    private void printVariables() {
        System.out.println("X: " + xPos);
        System.out.println("Y: " + yPos);
        System.out.println("Z: " + zPos);
        System.out.println("Rotate Angle: " + rotateAngle);
        System.err.println("COS: " + Math.cos(Math.toRadians(rotateAngle)));
        System.err.println("SIN: " + Math.sin(Math.toRadians(rotateAngle)));
    }

    private void printLightVariables() {
        System.out.println("A: " + a + " B: " + b + " C: " + c + " D: " + d);
    }
}
