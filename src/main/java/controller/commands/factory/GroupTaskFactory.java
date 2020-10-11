package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.GroupTask;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupTaskFactory extends AbstractTaskFactory
{
    private final List<ShapeComponent> selection;

    public GroupTaskFactory(List<ShapeComponent> selection)
    {
        this.selection = selection;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var shapes = new ArrayList<>(selection);
        var shapeGroup = new ShapeGroup(shapes);

        return new GroupTask(shapes, shapeGroup);
    }
}
