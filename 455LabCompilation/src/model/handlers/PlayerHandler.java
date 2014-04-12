package model.handlers;

import camera.Camera;
import model.mapObjects.destructible.EnemyEntity;
import org.lwjgl.util.vector.Vector4f;

public class PlayerHandler {
    int maxHp = 100;
    int hp = 100;

    private Camera camera;
    private EnemyHandler enemyHandler;

    public PlayerHandler(Camera camera, EnemyHandler enemyHandler) {
        this.camera = camera;
        this.enemyHandler = enemyHandler;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public float getPercentLife() {
        return (float)hp / maxHp;
    }

    public void loseHp(int hpToLose) {
        if(hp > 0)
            hp -= hpToLose;
        if(hp < 0)
            hp = 0;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public Vector4f getPlayerPosition() {
        return camera.getPositionVector();
    }

    public Camera getCamera() {
        return camera;
    }

    public void checkForCollisions() {
        checkForEnemyCollisions();
        // checkForObjectCollisions();
    }

    private void checkForEnemyCollisions() {
        EnemyEntity enemyHit = enemyHandler.findEnemyHitByPlayer(camera.getPositionVector());
        if (enemyHit != null) {
            loseHp(1);
        }
    }

    // TODO: Object collisions
    // TODO: make sure you can't go outside of the level

    public void moveForward() {
        camera.moveForward();
    }

    public void moveBackward() {
        camera.moveBackward();
    }

    public void moveRight() {
        camera.moveRight();
    }

    public void moveLeft() {
        camera.moveLeft();
    }

    public void moveUp() {
        camera.moveUp();
    }

    public void moveDown() {
        camera.moveDown();
    }

    public void turnLeft() {
        camera.turnLeft();
    }

    public void turnRight() {
        camera.turnRight();
    }

    public void positionCamera() {
        camera.positionCamera();
    }

    public void resetPosition() {
        camera.resetPosition(); // TODO: Remove in actual gameplay
    }

    private void updatePlayerPyramid() {
//        ShapeRenderer.drawPyramid
        // TODO: Draw pyramid that faces the direction of the player
        // this will mostly be helpful for the orthographic view displayed to the hud
    }
}
