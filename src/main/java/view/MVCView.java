package view;

import view.api.CanvasSvc;
import view.ui.UISvc;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;
import view.paintcanvas.PaintCanvas;
import view.ui.Gui;
import view.ui.GuiWindow;

public class MVCView
{
    public static void createView()
    {
        var paintCanvas = new PaintCanvas();
        CanvasSvc.init(paintCanvas);
        IGuiWindow guiWindow = new GuiWindow(paintCanvas, 1250, 800, "MisterShape");
        IUiModule ui = new Gui(guiWindow);
        UISvc.init(ui);
    }
}
