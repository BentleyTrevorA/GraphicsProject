package camera;

import model.Model;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
    // Right handed coordinate system - These values (numeric) are inverted because of the translation matrix
    public double xPos = 0;  // X = Right
    public double yPos = 5; // Y = Up
    public double zPos = 20;  // Z = Back

    public double rotateAngle = 0;

    private double rotateSpeed = 1.5;
    private double movementSpeed = 1.2;

    private boolean printVariables = false;
    private double cosineOfAngle = 0;
    private double sineOFAngle = 0;

    public Camera() {

    }

    public void positionCamera() {
        // TODO: This messes with the Orthographic projection
        glRotated(rotateAngle, 0f, 1.0f, 0f);
        glTranslated(-xPos, -yPos, -zPos);
    }

    public boolean isFacingForward() {
        return Math.cos(Math.toRadians(rotateAngle)) > 0;
    }

    public void moveForward() {
        zPos -= movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        xPos += movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
        remainInsidePlayingField();
    }

    public void moveBackward() {
        zPos += movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        xPos -= movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
        remainInsidePlayingField();
    }

    public void moveLeft() {
        xPos -= movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        zPos -= movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
        remainInsidePlayingField();
    }

    public double xCharAdjust(double distanceToMoveInX, double distanceToMoveInZ) {
        double dxX = -1 * distanceToMoveInX * movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        double dxZ = -1 * distanceToMoveInZ * Math.sin(Math.toRadians(rotateAngle));
        return dxX + dxZ;
    }

    public double zCharAdjust(double distanceToMoveInX, double distanceToMoveInZ) {
        double dzX = -1 * distanceToMoveInX * Math.sin(Math.toRadians(rotateAngle));
        double dzZ = distanceToMoveInZ * Math.cos(Math.toRadians(rotateAngle));
        return dzX + dzZ;
    }

    public void moveRight() {
        xPos += movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        zPos += movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
        remainInsidePlayingField();
    }

    public void moveUp() {
        yPos += movementSpeed;
        printCameraVariables();
    }

    public void moveDown() {
        yPos -= movementSpeed;
        printCameraVariables();
    }

    public void turnLeft() {
        rotateAngle -= rotateSpeed;
        printCameraVariables();
    }

    public void turnRight() {
        rotateAngle += rotateSpeed;
        printCameraVariables();
    }

    public void remainInsidePlayingField() {
        if(zPos >= Model.MAX_MAP_COORDINATE - 1)
            zPos = Model.MAX_MAP_COORDINATE - 1;
        if(zPos <= Model.MIN_MAP_COORDINATE + 1)
            zPos = Model.MIN_MAP_COORDINATE + 1;

        if(xPos >= Model.MAX_MAP_COORDINATE - 1)
            xPos = Model.MAX_MAP_COORDINATE - 1;
        if(xPos <= Model.MIN_MAP_COORDINATE + 1)
            xPos = Model.MIN_MAP_COORDINATE + 1;
    }

    public Vector4f getPositionVector(){
        return new Vector4f((float)xPos, (float)yPos, (float)zPos, 1);
    }

    public void resetPosition() {
        xPos = 0;
        yPos = 5;
        zPos = 20;
        rotateAngle = 0;
        printCameraVariables();
    }

    public void enablePrintValues() {
        printVariables = true;
    }

    public void disablePrintValues() {
        printVariables = false;
    }

    private void printCameraVariables() {
        if (printVariables) {
            System.out.println("X: " + xPos);
            System.out.println("Y: " + yPos);
            System.out.println("Z: " + zPos);
            System.out.println("Rotate Angle: " + rotateAngle);
            System.err.println("COS: " + Math.cos(Math.toRadians(rotateAngle)));
            System.err.println("SIN: " + Math.sin(Math.toRadians(rotateAngle)));
        }
    }
}
