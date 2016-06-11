package model;

import java.awt.geom.Path2D;
import java.util.Arrays;

/**
 * Created by Kamil on 2016-05-09.
 */
public class AsteroidShapeGenerator implements ShapeGenerator
{
    private static final int MIN_VERTICES = 4;
    private static final int MAX_VERTICES = 8;
    private double radius;

    public AsteroidShapeGenerator(double radius)
    {
        this.radius = radius;
    }

    /**
     * TODO: test, mock Math.random()?
     */
    private double[] generateVerticesAngles()
    {
        int numberOfPolygonVertices = (int) (Math.random() * (MAX_VERTICES - MIN_VERTICES + 1) + MIN_VERTICES);
        double[] verticesAngles = new double[numberOfPolygonVertices];
        for (int i = 0; i < numberOfPolygonVertices; ++i)
        {
            verticesAngles[i] = Math.random() * 360;
        }
        Arrays.sort(verticesAngles);
        return verticesAngles;
    }

    private int findIndexBeforeBiggestGap(double[] verticesAngles)
    {
        double biggestGap = 0;
        int index = 0;
        for (int i = 0; i < verticesAngles.length; ++i)
        {
            int next = (i + 1) % verticesAngles.length;
            double currentGap = verticesAngles[next] - verticesAngles[i];
            if (currentGap < 0)
                currentGap += 360;
            if (currentGap >= biggestGap)
            {
                biggestGap = currentGap;
                index = i;
            }
        }
        return index;
    }

    private int findBestIndexToReplace(double[] verticesAngles)
    {
        double smallestGap = 360;
        int index = 0;
        // best vertex to delete - when the gap between 2 vertices after deleting this vertex
        // will be as small as possible
        for (int i = 0; i < verticesAngles.length; ++i)
        {
            int next = (i + 2) % verticesAngles.length;
            double currentGap = verticesAngles[next] - verticesAngles[i];
            if (currentGap < 0)
                currentGap += 360;
            if (currentGap <= smallestGap)
            {
                smallestGap = currentGap;
                index = (i + 1) % verticesAngles.length;
            }
        }
        return index;
    }

    private void adjustShape(double[] verticesAngles)
    {
        int indexBeforeBiggestGap = findIndexBeforeBiggestGap(verticesAngles);
        int indexAfterBiggestGap = (indexBeforeBiggestGap + 1) % verticesAngles.length;
        double gapLength = verticesAngles[indexAfterBiggestGap] - verticesAngles[indexBeforeBiggestGap];
        if (gapLength < 0)
            gapLength += 360;
        if (gapLength > 180)
        {
//            int vertexToReplace = (indexAfterBiggestGap+1)%verticesAngles.length;
            int vertexToReplace = findBestIndexToReplace(verticesAngles);
            double additionalVertex = (verticesAngles[indexBeforeBiggestGap] + gapLength / 2) % 360;
            verticesAngles[vertexToReplace] = additionalVertex;
            Arrays.sort(verticesAngles);
        }
    }

    private void generateCoordinatesFromAngles(double xCoords[], double yCoords[], double[] verticesAngles)
    {
        for (int i = 0; i < verticesAngles.length; ++i)
        {
            xCoords[i] = radius * Math.sin(Math.toRadians(verticesAngles[i]));
            yCoords[i] = radius * Math.cos(Math.toRadians(verticesAngles[i]));
        }
    }

    private Path2D.Double generateShapeFromVertices(double[] xCoords, double[] yCoords)
    {
        Path2D.Double asteroidPath = new Path2D.Double();
        asteroidPath.moveTo(xCoords[0], yCoords[0]);
        for (int i = 1; i < xCoords.length; ++i)
        {
            asteroidPath.lineTo(xCoords[i], yCoords[i]);
        }
        asteroidPath.closePath();
        return asteroidPath;
    }

    @Override
    public MyShape generateShape()
    {
        double[] verticesAngles = generateVerticesAngles();
        adjustShape(verticesAngles);
        double[] xCoords = new double[verticesAngles.length];
        double[] yCoords = new double[verticesAngles.length];
        generateCoordinatesFromAngles(xCoords, yCoords, verticesAngles);
        Path2D.Double asteroidPath = generateShapeFromVertices(xCoords, yCoords);
        MyShape shape = new MyShape(asteroidPath, 0, 0);
        return shape;
    }
}
