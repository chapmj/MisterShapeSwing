package view.api;

import view.interfaces.IUiModule;
import view.viewstate.PaintCanvas;
import view.viewstate.PaintCanvasBase;
import view.viewstate.ViewState;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CanvasSvc
{
    private static final Supplier<Graphics2D> graphicsSupplier = () -> ViewState.getCanvas().getGraphics2D();
    private static final Supplier<PaintCanvasBase> canvasSupplier = () -> ViewState.getCanvas();
    private static final Consumer<MouseListener> mouseConsumer = (MouseListener mouse) -> ViewState.getCanvas().addMouseListener(mouse);
    public static Graphics2D getGraphics()
    {
        return graphicsSupplier.get();
    }

    public static int getCanvasWidth()
    {
        return canvasSupplier.get().getWidth();
    }

    public static int getCanvasHeight()
    {
        return canvasSupplier.get().getHeight();
    }

    public static void accept(MouseListener mouse)
    {
        mouseConsumer.accept(mouse);
    }
}
