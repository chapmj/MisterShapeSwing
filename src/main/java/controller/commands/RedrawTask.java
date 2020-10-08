package controller.commands;

import model.shape.ShapeComponent;
import view.Redraw;

import java.util.List;

public class RedrawTask extends AbstractControllerTask
{

    private final List<ShapeComponent> shapeComponents;

    public RedrawTask(List<ShapeComponent> shapeComponents)
    {
        this.shapeComponents = shapeComponents;
    }

    @Override
    public void execute()
    {
        try
        {
            Redraw.execute(this.shapeComponents);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
