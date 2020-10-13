package view.api;

import model.interfaces.IShape;
import view.EventName;
import view.commands.RedrawTask;
import view.interfaces.IEventCallback;
import view.viewstate.ViewState;

import java.util.List;

public class ViewAPI
{
    public static void redraw(List<IShape> shapes)
    {
        var task = new RedrawTask(shapes);
        task.execute();
    }

    public static void addGuiEvent(EventName eventName, IEventCallback callback) throws Exception
    {
        ViewState.getUI().addEvent(eventName, callback);
    }
}
