package model.handlers;

import model.renderers.ShapeRenderer;

public class HudHandler {
    private ShotsHandler shotsHandler;
    private ScoreHandler scoreHandler;
    private EnemyHandler enemyHandler;
    private PlayerHandler playerHandler;
    private HudTextHandler hudTextHandler;
    private HudVisualHandler hudVisualHandler;


    public HudHandler(ScoreHandler scoreHandler,
                      ShotsHandler shotsHandler,
                      EnemyHandler enemyHandler,
                      PlayerHandler playerHandler,
                      ShapeRenderer shapeRenderer) {
        hudTextHandler = new HudTextHandler();
        hudVisualHandler = new HudVisualHandler(shapeRenderer);
        this.scoreHandler = scoreHandler;
        this.shotsHandler = shotsHandler;
        this.enemyHandler = enemyHandler;
        this.playerHandler = playerHandler;
    }

    public void drawHud() {
        hudTextHandler.drawShotsRemaining(shotsHandler.getShotsRemaining());
        hudTextHandler.drawPoints(scoreHandler.getPoints());
        hudTextHandler.drawNumEnemies(enemyHandler.getEnemiesRemaining());
        hudTextHandler.drawHpRemaining(playerHandler.getHp(), playerHandler.getMaxHp());
        hudVisualHandler.drawLife(playerHandler.getPercentLife());
    }
}
