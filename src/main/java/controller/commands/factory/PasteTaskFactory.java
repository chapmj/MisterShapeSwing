package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.PasteTask;
import model.PointInt;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;

public class PasteTaskFactory extends AbstractTaskFactory
{
    private final PointInt pasteLocation;
    private final List<ShapeComponent> componentBuffer;

    public PasteTaskFactory(PointInt pasteLocation, List<ShapeComponent> componentBuffer)
    {
        this.pasteLocation = pasteLocation;
        this.componentBuffer = componentBuffer;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var shapeCopies = new ArrayList<>(componentBuffer);
        return new PasteTask(pasteLocation, shapeCopies);
    }
}
