package view.drawstrategy;

import model.interfaces.IShape;
import model.shape.ShapeComponent;

public class DrawStrategyNull extends DrawStrategy
{
    public DrawStrategyNull(ShapeComponent component)
    {
        super((IShape) component);
    }

    @Override
    public void draw()
    { }
}
