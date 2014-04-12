package model.handlers;

import camera.Camera;
import model.mapObjects.destructible.Shot;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.EnemyEntity;
import model.renderers.ShapeRenderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShotsHandler {
    private EnemyHandler enemyHandler;
    private ObstacleHandler obstacleHandler;
    private ShapeRenderer shapeRenderer;

    private Collection<Shot> shots;
    private int maxShots = 1;
    private Set<Shot> shotsToRemove;

    public ShotsHandler(ObstacleHandler obstacleHandler, EnemyHandler enemyHandler, ShapeRenderer shapeRenderer) {
        this.obstacleHandler = obstacleHandler;
        this.enemyHandler = enemyHandler;
        this.shapeRenderer = shapeRenderer;
        shots = new HashSet<Shot>();
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

    public void addShot(Camera camera) {
        if (shots.size() < maxShots) {
            Shot shot = new Shot(camera);
            shot.setShapeRenderer(shapeRenderer);
            shots.add(shot);
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
