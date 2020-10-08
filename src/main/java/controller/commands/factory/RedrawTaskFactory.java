package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.RedrawTask;
import model.shape.ShapeComponent;

import java.util.List;
import java.util.function.Supplier;

public class RedrawTaskFactory extends AbstractTaskFactory
{
    private final Supplier<List<ShapeComponent>> allShapesSupplier;

    public RedrawTaskFactory(Supplier<List<ShapeComponent>> allShapesSupplier)
    {
        this.allShapesSupplier = allShapesSupplier;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        return new RedrawTask(allShapesSupplier.get());
    }
}
