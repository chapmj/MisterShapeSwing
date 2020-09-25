package app;

import controller.CanvasController;
import controller.JPaintController;
import controller.MouseController;
import model.Gui;
import model.persistence.ApplicationState;
import view.gui.GuiWindow;
import view.gui.PaintCanvas;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

public class App
{
    public static void main (String[] args) throws Exception
    {
        System.out.println("Hello");

        // Create application UI.  GUI skeleton provided.
        // Starts event loop threads.
        PaintCanvasBase paintCanvas = new PaintCanvas();
        IGuiWindow guiWindow = new GuiWindow(paintCanvas);
        IUiModule uiModule = new Gui(guiWindow);
        ApplicationState appState = new ApplicationState(uiModule);

        // Create controller singletons
        JPaintController.createInstance(uiModule, appState, paintCanvas);
        CanvasController.createInstance(paintCanvas);
        CanvasController canvasController = CanvasController.getInstance();
        canvasController.setup();
        JPaintController.getInstance().setup();
        MouseController.createInstance();

        // Necessary to make the quirky Swing canvas stay up to date.
        Thread.sleep(500);

    }
}
