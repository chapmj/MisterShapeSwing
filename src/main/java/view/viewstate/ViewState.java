package view.viewstate;

import view.interfaces.IUiModule;
import view.paintcanvas.AbstractPaintCanvas;

public class ViewState
{
    private static ViewState viewState;

    private final AbstractPaintCanvas abstractPaintCanvas;
    private final IUiModule uiModule;

    public ViewState (AbstractPaintCanvas abstractPaintCanvas, IUiModule uiModule)
    {
        this.abstractPaintCanvas = abstractPaintCanvas;
        this.uiModule = uiModule;
        ViewState.viewState = this;
    }

    /*
    public static AbstractPaintCanvas getCanvas ()
    {
        return viewState.abstractPaintCanvas;
    }*/

    /*
    public static IUiModule getUI()
    {
        try
        {
            if (ViewState.viewState == null)
            {
                throw new Exception("ViewState not found");
            }
            else
            {
                return viewState.uiModule;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }*/
}
