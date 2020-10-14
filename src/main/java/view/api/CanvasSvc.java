package view.api;

import model.interfaces.IShape;
import view.commands.RedrawTask;
import view.paintcanvas.PaintCanvas;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

public class CanvasSvc
{
    private static PaintCanvas paintCanvas;

    public static void init(PaintCanvas paintCanvas)
    {
        CanvasSvc.paintCanvas = paintCanvas;
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
        var g = paintCanvas.getGraphics();
        g.setColor(SystemColor.WHITE);
        g.fillRect(0, 0, getCanvasWidth(), getCanvasHeight());

        var task = new RedrawTask(iShapes);
        task.execute();

    }

    // Make a big white rectangle the size of the canvas.
}
