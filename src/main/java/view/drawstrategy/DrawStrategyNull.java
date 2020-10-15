package view.drawstrategy;

import model.interfaces.IShape;

public class DrawStrategyNull extends DrawStrategy
{
    public DrawStrategyNull(IShape component)
    {
        super();
    }

    @Override
    public void draw()
    { }
}
