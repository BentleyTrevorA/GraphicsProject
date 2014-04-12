package model.handlers;

import camera.Camera;
import game.GameController;
import model.Model;
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
    private TextureHandler textureHandler;

    private Collection<EnemyEntity> enemies;
    private int maxEnemies = 30;

    public EnemyHandler(ScoreHandler scoreHandler, TextureHandler textureHandler) {
        enemies = new HashSet<EnemyEntity>();
        this.scoreHandler = scoreHandler;
        this.textureHandler = textureHandler;
//        populateTestEnemies();/
    }

    public void updateEnemies(Camera camera) {
        for (EnemyEntity enemy : enemies) {
            enemy.updatePosition();
//            enemy.updateTargetPosition(camera);
        }
        populateEnemies();
    }

    public void populateTestEnemies() {
        for(int i=0; i<=6; i++){
            EnemyEntity test = new CubeEnemy(i * 15, i * 15, textureHandler);
            test.setHp(6 - i);
            test.setPointValue(test.getHp() * 10);
            enemies.add(test);
        }
    }

    public void populateEnemies() {
        int xScale = Model.MAX_MAP_COORDINATE;
        int zScale = Model.MAX_MAP_COORDINATE;

        while(enemies.size() < maxEnemies) {
            EnemyEntity test = new CubeEnemy(Math.random() * xScale, Math.random() * zScale, textureHandler);
            if(enemies.size() % 5 == 0) {
                test.setHp(2);
            }
            enemies.add(test);
            if(enemies.size() % 2 == 1)
                xScale *= -1;
            if(enemies.size() % 4 == 1)
                zScale *= -1;
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
            Vector4f myPosition = new Vector4f(position.x, position.y, position.z, 1);
            if (enemy.isCollidingWith(myPosition)) {
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
