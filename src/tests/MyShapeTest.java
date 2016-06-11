package tests;

import model.MyShape;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kamil on 2016-05-22.
 */
public class MyShapeTest
{
    private MyShape shape;
    private Path2D.Double rectangle;

    @Before
    public void setUp() throws Exception
    {
        Rectangle2D.Double rectangleShape = new Rectangle2D.Double(0, 0, 20, 30);
        rectangle = new Path2D.Double(rectangleShape);
        shape = new MyShape(rectangle, 10, 15);
    }

    @Test
    public void myShapeReturnsProperShape()
    {
        shape.setPosition(500, 400);
        shape.setRotation(90);
        Path2D.Double returnedPath = shape.getShape();
        Path2D.Double expectedPath = new Path2D.Double(new Rectangle2D.Double(485, 390, 30, 20));
        boolean condition1 = returnedPath.contains(expectedPath.getBounds2D());
        boolean condition2 = expectedPath.contains(returnedPath.getBounds2D());
        assertTrue(condition1 && condition2);
    }

}