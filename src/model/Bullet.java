package model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Kamil on 2016-05-14.
 */
public class Bullet
{
    private final long timeToLive = 2000000000L;
    private final double bulletRadius = 5;
    private long shotTime;
    private double xPosition;
    private double yPosition;
    private double xVelocity;
    private double yVelocity;
    private Player owner;
    private Ellipse2D shape;

    public Bullet(Player owner, double xPosition, double yPosition, double xVelocity, double yVelocity, long currentTime)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.owner = owner;
        this.shotTime = currentTime;
        this.shape = new Ellipse2D.Double();
    }

    private void adjustPositionWhenOffScreen()
    {
        if (this.xPosition >= GameModel.getGameAreaWidth())
            this.xPosition -= GameModel.getGameAreaWidth();
        if (this.xPosition < 0)
            this.xPosition += GameModel.getGameAreaWidth();
        if (this.yPosition >= GameModel.getGameAreaHeight())
            this.yPosition -= GameModel.getGameAreaHeight();
        if (this.yPosition < 0)
            this.yPosition += GameModel.getGameAreaHeight();
    }

    private void updatePosition()
    {
        xPosition += xVelocity;
        yPosition += yVelocity;
    }

    public void update(long currentTime)
    {
        if (currentTime - shotTime > timeToLive)
        {
            owner.removeBullet(this, false);
            return;
        }
        updatePosition();
        adjustPositionWhenOffScreen();
    }

    public double getxPosition()
    {
        return xPosition;
    }

    public double getyPosition()
    {
        return yPosition;
    }

    public double getxVelocity()
    {
        return xVelocity;
    }

    public double getyVelocity()
    {
        return yVelocity;
    }

    public Shape getShape()
    {
        shape.setFrame(xPosition, yPosition, bulletRadius, bulletRadius);
        return shape;
    }
}
