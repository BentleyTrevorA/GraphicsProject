package model.handlers;

import camera.Camera;
import model.mapObjects.destructible.Shot;
import model.mapObjects.destructible.CubeEnemy;
import model.mapObjects.destructible.EnemyEntity;
import model.renderers.ShapeRenderer;

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
//            enemy.updateTargetPosition(new SphereObstacle(GameController.a, 5, GameController.b));
//            enemy.updateTargetPosition(camera); // TODO: Enable - also enable damage
        }
    }

    public void populateTestEnemies() {
        for(int i=0; i<6; i++)
        {
            EnemyEntity test = new CubeEnemy(i * 15, i * 15, shapeRenderer);
            test.setHp(6 - i);
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

    public void damageEnemy(EnemyEntity enemy, Shot shot) {
        enemy.loseHp(shot.getDamage());

        if (enemy.isDead()) {
            enemies.remove(enemy);
            scoreHandler.addPoints(enemy);
        }
    }
}
