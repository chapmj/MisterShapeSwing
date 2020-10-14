package view.drawstrategy;

import model.Selection;
import model.interfaces.IBoundary;
import model.interfaces.IShape;
import model.persistence.ModelState;
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

    private void drawSelection() {
        var selection = SelectionSvc.get();
        if(selection.contains(this.shape))
        {
            IBoundary selectionBoundary = new Selection(shape, 10).getSelectionShape();
            var x = selectionBoundary.getAnchor().getX();
            var y = selectionBoundary.getAnchor().getY();
            var w = selectionBoundary.getWidth();
            var h = selectionBoundary.getHeight();

            graphics.setColor(Color.BLACK);
            graphics.setStroke(stroke);
            graphics.drawRect(x, y, w, h);
        }

        double scale = 1.2f;
        ModelState.getShapeComponentSelectionList().stream()
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
