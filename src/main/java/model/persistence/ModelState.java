package model.persistence;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;

import java.util.List;

/***
 * Provide global access to CanvasState, Application State
 */
public class ModelState
{
    private static IApplicationState applicationState;
    private static CanvasState canvasState;

    public ModelState(IApplicationState applicationState, CanvasState canvasState)
    {
        ModelState.applicationState = applicationState;
        ModelState.canvasState = canvasState;
    }

    //provide shape selection list

    public static CanvasState getCanvasState()
    {
        return ModelState.canvasState;
    }

    public static CanvasStateSubject getCanvasStateSubject()
    {
        return ModelState.canvasState.canvasStateSubject;
    }

    public static IApplicationState getApplicationState()
    {
        return ModelState.applicationState;
    }

    public static List<IShape> getShapeComponentSelectionList()
    {
        return ModelState.canvasState.getComponentSelectionList();
    }

    public static void refresh()
    {
        try
        {
            canvasState.canvasStateSubject.notifyObservers();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
