package view.drawstrategy;

import model.interfaces.IShape;
import view.api.CanvasSvc;

public class DrawStrategyFactory
{
    static DrawStrategy create(IShape component)
    {
        var graphics = CanvasSvc.getGraphics();
        switch (component.getType())
        {
            case RECTANGLE:
                return new DrawStrategyRectangle(component, graphics);
            case ELLIPSE:
                return new DrawStrategyEllipse(component, graphics);
            case TRIANGLE:
                return new DrawStrategyTriangle(component, graphics);
            case INVISIBLE_RECT:
                return new DrawStrategyInvisibleRect(component, graphics);
            default:
                return new DrawStrategyNull(component);
        }
    }
}
