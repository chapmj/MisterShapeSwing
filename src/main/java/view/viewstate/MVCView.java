package view.viewstate;

import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;

public class MVCView
{
    static ViewState viewState;
    public MVCView()
    {

    }

    public static void createView()
    {
        //SwingUIRef
        PaintCanvasBase paintCanvasBase = new PaintCanvas();//isview
        IGuiWindow guiWindow = new GuiWindow(paintCanvasBase, 1250, 800, "MisterShape");//isview
        IUiModule uiModule = new Gui(guiWindow);//isview

        MVCView.viewState = new ViewState(paintCanvasBase, uiModule);

    }

}
