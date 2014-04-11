package model.handlers;

import camera.Camera;
import game.Shot;
import model.Colors;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.EnemyEntity;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShotsHandler {
    private EnemyHandler enemyHandler;

    private ArrayList<Shot> shots;
    private int maxShots = 1;
    private Set<Shot> shotsToRemove;
    private Vector3f shotColor = Colors.PINK; // TODO: Move to shot

    public ShotsHandler(EnemyHandler enemyHandler) {
        this.enemyHandler = enemyHandler;
        shots = new ArrayList<Shot>();
    }

    public int getShotsRemaining() {
        return maxShots - shots.size();
    }

    /* **********************************************
     *                  SHOTS
     * *********************************************/
    public void drawShots() {
        for (Shot shot : shots) {
            ShapeRenderer.drawSphere(shot.size, shot.x, shot.y, shot.z, shot.slices, shot.stacks, shotColor);
        }
    }

    public void addShot(Camera camera) {
        if (shots.size() < maxShots) {
            shots.add(new Shot(camera));
        }
    }

    public void updateShots(Collection<MapObject> obstacles) {
        shotsToRemove = new HashSet<Shot>();

        for (Shot shot : shots) {
            updateShotPositions(shot);
            checkForEnemyCollisions(shot);
            checkForObstacleCollisions(shot, obstacles);
            shot.checkShotLife();

            if(shot.isDead())
                shotsToRemove.add(shot);
        }

        removeDeadShots();
    }

    private void updateShotPositions(Shot shot) {
        shot.updatePosition();

        if (shot.isOutsideGameField())
            shot.kill();
    }

    private void checkForEnemyCollisions(Shot shot) {
        if(shot.isDead())
            return;

        EnemyEntity enemyHit = enemyHandler.findEnemyHitByShot(shot);
        if (enemyHit != null) {
            enemyHandler.damageEnemy(enemyHit, shot);
            shot.kill();
        }
    }

    private void checkForObstacleCollisions(Shot shot, Collection<MapObject> obstacles) {
        if(shot.isDead())
            return;

        MapObject objectHit = findObstacleHitByShot(shot, obstacles);
        if (objectHit != null) {
            System.out.println("I'm hit!\n" + objectHit);

            // TODO: Reflect correctly
//                        if(objectHit.getCollisionPlane() == MapObject.Z_PLANE)
            shot.dz *= -1;
//                        else if(objectHit.getCollisionPlane() == MapObject.X_PLANE)
//                            shot.dx *= -1;
        }
    }

    private void removeDeadShots() {
        for (Shot shot : shotsToRemove) {
            shots.remove(shot);
        }
    }

    private MapObject findObstacleHitByShot(Shot shot, Collection<MapObject> obstacles) {
        for(MapObject obstacle : obstacles) {
            if(obstacle.isCollidingWith(shot.x, shot.y, shot.z)) {
                return obstacle;
            }
        }
        return null;
    }
}
