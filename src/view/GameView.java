package view;

import model.*;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Created by Kamil on 2016-05-01.
 */
public class GameView
{
    private static final Color immortalPlayerColor = Color.RED;
    private GameModel gameModel;

    public GameView(GameModel model)
    {
        gameModel = model;
    }

    private Path2D.Double preparePlayerShape(Player player)
    {
        MyShape playerShape = player.getShape();
        return playerShape.getShape();
    }

    private void renderPlayer(Graphics2D graphics)
    {
        Player player = gameModel.getPlayer();
        Path2D.Double playerShape = preparePlayerShape(player);
        Color originalColor = graphics.getColor();
        if (player.getState() == Player.State.IMMORTAL)
            graphics.setColor(immortalPlayerColor);
        graphics.draw(playerShape);
        graphics.setColor(originalColor);
    }

    private void renderBullets(Graphics2D graphics)
    {
        for (Bullet bullet : gameModel.getPlayer().getBullets())
        {
            graphics.draw(bullet.getShape());
        }
    }

    private void renderAsteroids(Graphics2D graphics)
    {
        for (Asteroid asteroid : gameModel.getAsteroidsManager().getAsteroids())
        {
            MyShape shape = asteroid.getShape();
            graphics.draw(shape.getShape());
        }
    }

    private void setRenderingOptions(Graphics2D graphics)
    {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public void render(Graphics g)
    {
        Graphics2D graphics = (Graphics2D) g;
        setRenderingOptions(graphics);
        renderPlayer(graphics);
        renderBullets(graphics);
        renderAsteroids(graphics);
    }
}
