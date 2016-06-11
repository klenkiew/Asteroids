package model;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Created by Kamil on 2016-05-09.
 */
public class MyShape
{
    private Path2D.Double path;
    private double anchorx;
    private double anchory;
    private AffineTransform affineTransformRotation = new AffineTransform();
    private AffineTransform affineTransformTranslation = new AffineTransform();

    public MyShape(Path2D.Double path, double anchorx, double anchory)
    {
        this.path = path;
        this.anchorx = anchorx;
        this.anchory = anchory;
    }

    public void setRotation(double degrees)
    {
        affineTransformRotation.setToRotation(Math.toRadians(degrees), anchorx, anchory);
    }

    public void setPosition(double x, double y)
    {
        affineTransformTranslation.setToTranslation(x - anchorx, y - anchory);
    }

    public Path2D.Double getShape()
    {
        AffineTransform affineTransform = new AffineTransform(affineTransformTranslation);
        affineTransform.concatenate(affineTransformRotation);
        Path2D.Double transformedPath = (Path2D.Double) path.clone();
        transformedPath.transform(affineTransform);
        return transformedPath;
    }
}
