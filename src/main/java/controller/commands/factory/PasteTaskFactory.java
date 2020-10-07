package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.PasteTask;
import model.PointInt;
import model.shape.ShapeComponent;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PasteTaskFactory extends AbstractTaskFactory
{
    private final Supplier<PointInt> pasteLocationSupplier;
    private final Supplier<List<ShapeComponent>> componentBufferSupplier;

    public PasteTaskFactory(Supplier<PointInt> pasteLocationSupplier, Supplier<List<ShapeComponent>> componentBufferSupplier)
    {
        this.pasteLocationSupplier = pasteLocationSupplier;
        this.componentBufferSupplier = componentBufferSupplier;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        var pasteLocation = pasteLocationSupplier.get();
        var componentBuffer = componentBufferSupplier.get();

        var shapeCopies = componentBuffer.stream()
                .map(ShapeComponent::clone)
                .collect(Collectors.toList());

        return new PasteTask(pasteLocation, shapeCopies);
    }
}
