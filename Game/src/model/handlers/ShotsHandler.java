package model.handlers;

import camera.Camera;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.destructible.Shot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShotsHandler {
    private static int maxShots = 10;
    private static int shotDelayMilliseconds = 125;

    private EnemyHandler enemyHandler;
    private ObstacleHandler obstacleHandler;
    private TextureHandler textureHandler;
    private SoundHandler soundHandler;

    private Collection<Shot> shots;
    private Set<Shot> shotsToRemove;

    private long lastShotTimeMilliseconds;
    private float timeTillNextShot; // TODO: Display how long until you can shoot again

    public ShotsHandler(ObstacleHandler obstacleHandler,
                        EnemyHandler enemyHandler,
                        TextureHandler textureHandler,
                        SoundHandler soundHandler) {
        this.obstacleHandler = obstacleHandler;
        this.enemyHandler = enemyHandler;
        this.textureHandler = textureHandler;
        this.soundHandler = soundHandler;

        shots = new HashSet<Shot>();
        lastShotTimeMilliseconds = 0;
    }

    public int getShotsRemaining() {
        return maxShots - shots.size();
    }

    /* **********************************************
     *                  SHOTS
     * *********************************************/
    public void drawShots() {
        for (Shot shot : shots) {
            shot.render();
        }
    }

    // TODO: Use Calendar.getSeconds() to only let you shoot every so often.
    public void addShot(Camera camera) {
        long timePassed = TimeHandler.getCurrentTimeInMilliseconds() - lastShotTimeMilliseconds;
        if (timePassed > shotDelayMilliseconds) {
            if (shots.size() < maxShots) {
                Shot shot = new Shot(camera);
                shot.setTextureHandler(textureHandler);
                shots.add(shot);
                soundHandler.getSoundEffect(SoundHandler.SHOT4).playAsSoundEffect(1, 1, false);
                lastShotTimeMilliseconds = TimeHandler.getCurrentTimeInMilliseconds();
            }
        }
    }

    public void updateShots() {
        shotsToRemove = new HashSet<Shot>();

        for (Shot shot : shots) {
            updateShotPosition(shot);
            checkForEnemyCollisions(shot);
            checkForObstacleCollisions(shot);
            shot.checkShotLife();

            if (shot.isDead())
                shotsToRemove.add(shot);
        }

        removeDeadShots();
    }

    private void updateShotPosition(Shot shot) {
        shot.updatePosition();

        if (shot.isOutsideGameField())
            shot.kill();
    }

    private void checkForEnemyCollisions(Shot shot) {
        if (shot.isDead())
            return;

        EnemyEntity enemyHit = enemyHandler.findEnemyHitByShot(shot);
        if (enemyHit != null) {
            enemyHandler.damageEnemy(enemyHit, shot);
            shot.kill();
        }
    }

    private void checkForObstacleCollisions(Shot shot) {
        if (shot.isDead())
            return;

        MapObject objectHit = obstacleHandler.findObstacleHitByShot(shot);
        if (objectHit != null) {
            System.out.println("I'm hit!\n" + objectHit);

            // TODO: Reflect correctly
//                        if(objectHit.getCollisionPlane() == MapObject.Z_PLANE)
            shot.setDz(shot.getDz() * -1);
//                        else if(objectHit.getCollisionPlane() == MapObject.X_PLANE)
//                            shot.dx *= -1;
        }
    }

    private void removeDeadShots() {
        for (Shot shot : shotsToRemove) {
            shots.remove(shot);
        }
    }
}
