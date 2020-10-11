package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.RedrawTask;
import model.shape.ShapeComponent;

import java.util.List;
import java.util.function.Consumer;

public class RedrawTaskFactory extends AbstractTaskFactory
{
    private final List<ShapeComponent> allShapes;
    private final Consumer<List<ShapeComponent>> redrawer;

    public RedrawTaskFactory(List<ShapeComponent> allShapes, Consumer<List<ShapeComponent>> redrawer)
    {
        this.allShapes = allShapes;
        this.redrawer = redrawer;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        return new RedrawTask(allShapes, redrawer);
    }
}
