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
    private final Supplier<List<ShapeComponent>> selection;

    public GroupTaskFactory(Supplier<List<ShapeComponent>> selection)
    {
        this.selection = selection;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var shapes = new ArrayList<>(selection.get());
        var shapeGroup = new ShapeGroup(shapes);

        return new GroupTask(shapes, shapeGroup);
    }
}
