package model.handlers;

import camera.Camera;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.destructible.Shot;

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

    public void loseHp(int hp) {
        if(hp > 0)
            this.hp -= hp;
        if(hp < 0)
            this.hp = 0;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void checkForCollisions() {
        checkForEnemyCollisions();
        // checkForObjectCollisions();
    }

    private void checkForEnemyCollisions() {
        EnemyEntity enemyHit = enemyHandler.findEnemyHitByPlayer(camera.getVector());
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
}
