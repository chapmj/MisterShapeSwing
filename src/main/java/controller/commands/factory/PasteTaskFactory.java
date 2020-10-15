package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.PasteTask;
import model.PointInt;
import model.interfaces.IShape;
import model.shape.ShapeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PasteTaskFactory extends AbstractTaskFactory
{
    private final Supplier<PointInt> pasteLocation;
    private final Supplier<List<IShape>> componentBuffer;

    public PasteTaskFactory(Supplier<PointInt> pasteLocation, Supplier<List<IShape>> componentBuffer)
    {
        this.pasteLocation = pasteLocation;
        this.componentBuffer = componentBuffer;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var shapeCopies = new ArrayList<>(componentBuffer.get());


        return new PasteTask(pasteLocation.get(), shapeCopies);
    }
}
