package model.handlers;

import model.mapObjects.destructible.Shot;
import model.mapObjects.destructible.CubeEnemy;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.destructible.SphereEnemy;

import java.util.Collection;
import java.util.HashSet;

public class EnemyHandler {
    private ScoreHandler scoreHandler;

    private Collection<EnemyEntity> enemies;

    public EnemyHandler(ScoreHandler scoreHandler) {
        enemies = new HashSet<EnemyEntity>();
        populateTestEnemies();
        this.scoreHandler = scoreHandler;
    }

    public void populateTestEnemies() {
        enemies.add(new CubeEnemy(0, 0));
        enemies.add(new CubeEnemy(0, -200, 50));
        enemies.add(new CubeEnemy(0, 200, 50));
        enemies.add(new SphereEnemy(150, 0));
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
