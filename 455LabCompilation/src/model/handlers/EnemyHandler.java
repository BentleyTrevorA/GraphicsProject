package model.handlers;

import camera.Camera;
import game.GameController;
import model.Model;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.Shot;
import model.mapObjects.destructible.CubeEnemy;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.destructible.SphereEnemy;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector4f;

import java.util.Collection;
import java.util.HashSet;

public class EnemyHandler {
    private ScoreHandler scoreHandler;
    private TextureHandler textureHandler;
    private SoundHandler soundHandler;

    private Collection<EnemyEntity> enemies;
    private int maxEnemies = 30;
    private int counter = 0;

    public EnemyHandler(ScoreHandler scoreHandler,
                        TextureHandler textureHandler,
                        SoundHandler soundHandler) {
        enemies = new HashSet<EnemyEntity>();
        this.scoreHandler = scoreHandler;
        this.textureHandler = textureHandler;
        this.soundHandler = soundHandler;
//        populateTestEnemies();/
    }

    public void updateEnemies(Camera camera) {
        for (EnemyEntity enemy : enemies) {
            enemy.updatePosition();
            enemy.updateTargetPosition(camera);
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
            EnemyEntity test;
            if(counter % 10 == 0) {
                test = new SphereEnemy(Math.random() * xScale, Math.random() * zScale, textureHandler);
            }
            else {
                test = new CubeEnemy(Math.random() * xScale, Math.random() * zScale, textureHandler);
                if(counter % 5 == 0) {
                    test.setHp(5);
                }
            }
            enemies.add(test);

            counter++;
            xScale *= -1;
            if(counter % 2 == 0)
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
        soundHandler.getSoundEffect(SoundHandler.ENEMY_HIT1).playAsSoundEffect(1, 1, false);

        if (enemy.isDead()) {
            enemies.remove(enemy);
            scoreHandler.addPoints(enemy);
        }
    }
}
