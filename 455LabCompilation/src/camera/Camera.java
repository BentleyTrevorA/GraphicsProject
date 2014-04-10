package camera;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
    // Right handed coordinate system - These values (numeric) are inverted because of the translation matrix
    public double xPos = 0;  // X = Right
    public double yPos = 5; // Y = Up
    public double zPos = 20;  // Z = Back

    public double rotateAngle = 0;

    private double rotateSpeed = 1;
    private double movementSpeed = 1;

    private boolean printVariables = false;

    public Camera() {

    }

    public void positionCamera() {
        glRotated(rotateAngle, 0f, 1.0f, 0f);
        glTranslated(-xPos, -yPos, -zPos);
    }

    public void moveForward() {
        zPos -= movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        xPos += movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
    }

    public void moveBackward() {
        zPos += movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        xPos -= movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
    }

    public void moveLeft() {
        xPos -= movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        zPos -= movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
    }

    public void moveRight() {
        xPos += movementSpeed * Math.cos(Math.toRadians(rotateAngle));
        zPos += movementSpeed * Math.sin(Math.toRadians(rotateAngle));
        printCameraVariables();
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
