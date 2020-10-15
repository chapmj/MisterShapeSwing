package view.drawstrategy;

import model.interfaces.IShape;
import view.api.SelectionSvc;

import java.awt.*;

import static view.Polygon.dilateTriangle;

public class DrawStrategyTriangleSelection extends DrawStrategy
{
    private final Graphics2D graphics;

    public DrawStrategyTriangleSelection(IShape shape, Graphics2D graphics)
    {
        super(shape);
        this.graphics = graphics;
    }

    @Override
    public void draw()
    {
        drawSelection();
    }

    private void drawSelection()
    {
        double scale = 1.2f;
        SelectionSvc.get().stream()
            .filter((s) -> s.equals(shape))
            .limit(1)
            .forEach((shape) ->
            {
                var x = shape.getAnchor().getX();
                var y = shape.getAnchor().getY();
                var w = shape.getWidth();
                var h = shape.getHeight();
                var cardinality = shape.getCardinality();

                graphics.setColor(Color.BLACK);
                graphics.setStroke(stroke);
                Polygon selectionTriangle = dilateTriangle(x, y, w, h, cardinality, scale);
                graphics.drawPolygon(selectionTriangle);
            });
    }
}
