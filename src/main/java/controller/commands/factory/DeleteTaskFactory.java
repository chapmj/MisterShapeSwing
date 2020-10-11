package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.DeleteTask;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeleteTaskFactory extends AbstractTaskFactory
{
    private final Supplier<List<ShapeComponent>> selectionSupplier;

    public DeleteTaskFactory(Supplier<List<ShapeComponent>> selectionSupplier)
    {
        this.selectionSupplier = selectionSupplier;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var shapes = new ArrayList<>(selectionSupplier.get());
	    return new DeleteTask(shapes);
    }

}
