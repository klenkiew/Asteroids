package model;

/**
 * Created by Kamil on 2016-05-04.
 */
public class Asteroid
{
    private Type type;
    private double xPosition;
    private double yPosition;
    private double xVelocity;
    private double yVelocity;
    private double xAcceleration;
    private double yAcceleration;
    private double rotation;
    private MyShape shape;

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public double getxPosition()
    {
        return xPosition;
    }

    public void setxPosition(double xPosition)
    {
        this.xPosition = xPosition;
    }

    public double getyPosition()
    {
        return yPosition;
    }

    public void setyPosition(double yPosition)
    {
        this.yPosition = yPosition;
    }

    public double getxVelocity()
    {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity()
    {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity)
    {
        this.yVelocity = yVelocity;
    }

    public double getxAcceleration()
    {
        return xAcceleration;
    }

    public void setxAcceleration(double xAcceleration)
    {
        this.xAcceleration = xAcceleration;
    }

    public double getyAcceleration()
    {
        return yAcceleration;
    }

    public void setyAcceleration(double yAcceleration)
    {
        this.yAcceleration = yAcceleration;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    public MyShape getShape()
    {
        shape.setPosition(xPosition, yPosition);
        shape.setRotation(rotation);
        return shape;
    }

    public void setShape(MyShape shape)
    {
        this.shape = shape;
    }

    public enum Type
    {
        SMALL(30),
        BIG(50);
        private double radius;

        Type(double radius)
        {
            this.radius = radius;
        }

        public double getRadius()
        {
            return radius;
        }
    }
}
