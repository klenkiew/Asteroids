package model;

import java.awt.geom.Path2D;

/**
 * Created by Kamil on 2016-05-09.
 */
public class PlayerShapeGenerator implements ShapeGenerator
{
    private final double PLAYER_LENGTH = 30;
    private final double PLAYER_WIDTH = 20;

    @Override
    public MyShape generateShape()
    {
        Path2D.Double path = new Path2D.Double();
        double[] xCords = new double[]{0, PLAYER_WIDTH, PLAYER_WIDTH / 2};
        double[] yCords = new double[]{PLAYER_LENGTH, PLAYER_LENGTH, 0};
        double centerOfGravityX = (xCords[0] + xCords[1]) / 2;
        double centerOfGravityY = (yCords[0] + yCords[2]) * 2 / 3;
        path.moveTo(xCords[0], yCords[0]);
        path.lineTo(centerOfGravityX, centerOfGravityY);
        for (int i = 1; i < xCords.length; ++i)
        {
            path.lineTo(xCords[i], yCords[i]);
        }
        path.closePath();
        MyShape shape = new MyShape(path, centerOfGravityX, centerOfGravityY);
        return shape;
    }
}