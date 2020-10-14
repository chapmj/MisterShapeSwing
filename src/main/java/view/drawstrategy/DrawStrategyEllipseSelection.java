package view.drawstrategy;

import model.Selection;
import model.interfaces.IBoundary;
import model.interfaces.IShape;
import view.api.CanvasSvc;
import view.api.SelectionSvc;

import java.awt.*;

public class DrawStrategyEllipseSelection extends DrawStrategy
{
    private final Graphics2D graphics;

    public DrawStrategyEllipseSelection(IShape shape, Graphics2D graphics)
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
        var selection = SelectionSvc.get();
        var graphics = CanvasSvc.getGraphics();

        if(selection.contains(this.shape))
        {
            IBoundary selectionBoundary = new Selection(shape, 10).getSelectionShape();
            var x = selectionBoundary.getAnchor().getX();
            var y = selectionBoundary.getAnchor().getY();
            var w = selectionBoundary.getWidth();
            var h = selectionBoundary.getHeight();

            graphics.setColor(Color.BLACK);
            graphics.setStroke(stroke);
            graphics.drawOval(x, y, w, h);
        }
    }
}
