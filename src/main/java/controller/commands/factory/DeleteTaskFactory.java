package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.DeleteTask;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeleteTaskFactory extends AbstractTaskFactory
{
    private final List<ShapeComponent> selection;

    public DeleteTaskFactory(List<ShapeComponent> selection)
    {
        this.selection= selection;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var shapes = new ArrayList<>(selection);
	    return new DeleteTask(shapes);
    }

}
