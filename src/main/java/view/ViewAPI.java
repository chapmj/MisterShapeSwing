package view;

import model.shape.ShapeComponent;
import view.commands.RedrawTask;

import java.util.List;

public class ViewAPI
{
    public static void redraw(List<ShapeComponent> shapes)
    {
        var task = new RedrawTask(shapes);
        task.execute();
    }
}
