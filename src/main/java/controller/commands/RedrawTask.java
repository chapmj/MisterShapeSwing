package controller.commands;

import model.api.ModelAPI;
import view.Redraw;

public class RedrawTask extends AbstractControllerTask
{
    @Override
    public void execute()
    {
        try
        {
            var shapeComponents = ModelAPI.getComponents();
            Redraw.execute(shapeComponents);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
