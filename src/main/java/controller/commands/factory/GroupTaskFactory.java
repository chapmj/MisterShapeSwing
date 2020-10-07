package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.GroupTask;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GroupTaskFactory extends AbstractTaskFactory
{
    private final Supplier<List<ShapeComponent>> selectionSupplier;

    public GroupTaskFactory(Supplier<List<ShapeComponent>> selectionSupplier)
    {
        this.selectionSupplier = selectionSupplier;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var selection = selectionSupplier.get();
        var shapes = new ArrayList<>(selection);
        var shapeGroup = new ShapeGroup(shapes);

        return new GroupTask(shapes, shapeGroup);
    }
}
