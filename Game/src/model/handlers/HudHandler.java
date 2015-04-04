package model.handlers;

import model.BasicSettings;

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
                      TextureHandler textureHandler) {
        hudTextHandler = new HudTextHandler();
        hudVisualHandler = new HudVisualHandler(textureHandler);
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

        if(BasicSettings.DRAW_MINI_MAP)
            hudVisualHandler.pushMatricesForMiniMap(playerHandler.getCamera());
    }

    public void finishDrawingHud() {
        hudVisualHandler.cleanupDrawingMiniMap();
    }
}
