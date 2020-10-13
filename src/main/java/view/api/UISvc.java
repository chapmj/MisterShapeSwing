package view.api;

import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;
import view.paintcanvas.PaintCanvas;
import view.ui.Gui;
import view.ui.GuiWindow;
import view.viewstate.ViewState;

import java.util.function.Supplier;

public class UISvc
{
    IUiModule ui;


    public UISvc(PaintCanvas paintCanvas)
    {
        IGuiWindow guiWindow = new GuiWindow(paintCanvas, 1250, 800, "MisterShape");
        this.ui = new Gui(guiWindow);
    }

    public IUiModule getUI()
    {
        return this.ui;
    }

}
