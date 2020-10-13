package view.api;

import view.paintcanvas.PaintCanvas;

import java.awt.*;
import java.awt.event.MouseListener;

public class CanvasSvc
{
    private static PaintCanvas paintCanvas = new PaintCanvas();

    public static void initialize()
    {
        paintCanvas = new PaintCanvas();
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

}
