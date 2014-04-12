package model.handlers;

import camera.Camera;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.Shot;
import model.mapObjects.destructible.CubeEnemy;
import model.mapObjects.destructible.EnemyEntity;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector4f;

import java.util.Collection;
import java.util.HashSet;

public class EnemyHandler {
    private ScoreHandler scoreHandler;
    private ShapeRenderer shapeRenderer;

    private Collection<EnemyEntity> enemies;

    public EnemyHandler(ScoreHandler scoreHandler, ShapeRenderer shapeRenderer) {
        enemies = new HashSet<EnemyEntity>();
        this.scoreHandler = scoreHandler;
        this.shapeRenderer = shapeRenderer;
        populateTestEnemies();
    }

    public void updateEnemies(Camera camera) {
        for (EnemyEntity enemy : enemies) {
            enemy.updatePosition();
            enemy.updateTargetPosition(camera);
        }
    }

    public void populateTestEnemies() {
        for(int i=0; i<6; i++) {
            EnemyEntity test = new CubeEnemy(i * 15, i * 15, shapeRenderer);
            test.setHp(6 - i);
            test.setPointValue(test.getHp() * 10);
            enemies.add(test);
        }
    }

    public void drawEnemies() {
        for (EnemyEntity enemy : enemies) {
            enemy.render();
        }
    }

    public int getEnemiesRemaining() {
        return enemies.size();
    }

    public EnemyEntity findEnemyHitByShot(Shot shot) {
        for (EnemyEntity enemy : enemies) {
            if (enemy.isCollidingWith(shot)) {
                return enemy;
            }
        }
        return null;
    }

    public EnemyEntity findEnemyHitByPlayer(Vector4f position) {
        for (EnemyEntity enemy : enemies) {
            if (enemy.isCollidingWith(position)) {
                return enemy;
            }
        }
        return null;
    }

    public void damageEnemy(EnemyEntity enemy, Shot shot) {
        enemy.loseHp(shot.getDamage());

        if (enemy.isDead()) {
            enemies.remove(enemy);
            scoreHandler.addPoints(enemy);
        }
    }
}
