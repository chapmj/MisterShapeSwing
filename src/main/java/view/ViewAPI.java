package view;

import model.shape.ShapeComponent;
import view.commands.RedrawTask;
import view.interfaces.IEventCallback;
import view.viewstate.ViewState;

import java.util.List;

public class ViewAPI
{
    public static void redraw(List<ShapeComponent> shapes)
    {
        var task = new RedrawTask(shapes);
        task.execute();
    }

    public static void addGuiEvent(EventName eventName, IEventCallback callback) throws Exception
    {
        ViewState.getUI().addEvent(eventName, callback);
    }
}
