package view.viewstate;

import view.api.CanvasSvc;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;
import view.ui.Gui;
import view.ui.GuiWindow;

public class MVCView
{
    private static ViewState viewState;

    public static void createView()
    {
        var paintCanvas = CanvasSvc.get();

        IGuiWindow guiWindow = new GuiWindow(paintCanvas, 1250, 800, "MisterShape");
        IUiModule uiModule = new Gui(guiWindow);

        MVCView.viewState = new ViewState(paintCanvas, uiModule);
    }
}
