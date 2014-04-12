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
        this.hp -= hp;
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
}
