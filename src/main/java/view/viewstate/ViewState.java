package view.viewstate;

import view.interfaces.IUiModule;

public class ViewState
{
    private static ViewState viewState;

    private final PaintCanvasBase paintCanvasBase;
    private final IUiModule uiModule;

    public ViewState (PaintCanvasBase paintCanvasBase, IUiModule uiModule)
    {
        this.paintCanvasBase = paintCanvasBase;
        this.uiModule = uiModule;
        ViewState.viewState = this;
    }

    public static PaintCanvasBase getCanvas ()
    {
        return viewState.paintCanvasBase;
    }

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
    }
}
