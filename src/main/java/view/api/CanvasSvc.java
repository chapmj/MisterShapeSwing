package view.api;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IShape;
import model.shape.*;
import model.shape.Shape;
import view.commands.RedrawTask;
import view.paintcanvas.PaintCanvas;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

public class CanvasSvc
{
    private static PaintCanvas paintCanvas;
    private static IShape clearRectangle;

    public static void init(PaintCanvas paintCanvas)
    {
        CanvasSvc.paintCanvas = paintCanvas;

        CanvasSvc.clearRectangle = new Shape(
            ShapeType.RECTANGLE,
            new Dimensions(getCanvasHeight(), getCanvasWidth()),
            new ShapeStyle(
                    ShapeColor.WHITE,
                    ShapeColor.WHITE,
                    ShapeShadingType.FILLED_IN),
            ShapeCardinality.NE,
            new PointInt(0,0));
    }

    public static PaintCanvas get()
    {
        return paintCanvas;
    }

    public static Graphics2D getGraphics()
    {
        return paintCanvas.getGraphics2D();
    }

    public static int getCanvasWidth()
    {
        return paintCanvas.getWidth();
    }

    public static int getCanvasHeight()
    {
        return paintCanvas.getHeight();
    }

    public static void accept(MouseListener mouse)
    {
        paintCanvas.addMouseListener(mouse);
    }

    public static void redraw(List<IShape> iShapes)
    {
        var task = new RedrawTask(iShapes);
        task.execute();
    }

    // Make a big white rectangle the size of the canvas.
    public static IShape clearCanvasShape()
    {
        return clearRectangle;
    }
}
