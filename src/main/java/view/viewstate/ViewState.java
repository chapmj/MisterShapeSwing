package view.viewstate;

import controller.MouseHandler;
import view.interfaces.IUiModule;
import controller.PaintCanvasBase;

import java.awt.*;

public class ViewState
{
    private static ViewState viewState;

    private final PaintCanvasBase paintCanvasBase;
    public final IUiModule uiModule;

    public ViewState (PaintCanvasBase paintCanvasBase, IUiModule uiModule)
    {
        this.paintCanvasBase = paintCanvasBase;
        this.uiModule = uiModule;
        ViewState.viewState = this;
    }

    public ViewState(PaintCanvasBase paintCanvasBase, MouseHandler mouseHandler, IUiModule uiModule)
    {
        this.paintCanvasBase = paintCanvasBase;
        this.uiModule = uiModule;
        this.paintCanvasBase.addMouseListener(mouseHandler);
        ViewState.viewState = this;
    }

    public static void update(ViewState viewState)
    {
        ViewState.viewState = viewState;
    }

    public static Graphics2D getGraphics()
    {
        try
        {
            if (ViewState.viewState == null)
            {
                throw new Exception("ViewState not found");
            }
            else if (ViewState.viewState.paintCanvasBase == null)
            {
                throw new Exception("ViewState graphics object not found");
            }
            else
            {
                return ViewState.viewState.paintCanvasBase.getGraphics2D();
            }
        } catch (Exception e)
        {
           e.printStackTrace();
           return null;
        }
    }

    public static PaintCanvasBase getCanvas()
    {
        return ViewState.viewState.paintCanvasBase;
    }

    public static IUiModule getUI() throws Exception
    {
        if(ViewState.viewState == null)
        {
            throw new Exception("ViewState not found");
        }
        else
        {
            return viewState.uiModule;
        }
    }
}
