package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.RedrawTask;
import model.shape.ShapeComponent;
//import view.commands.RedrawTask;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RedrawTaskFactory extends AbstractTaskFactory
{
    private final Supplier<List<ShapeComponent>> allShapesSupplier;
    private final Consumer<List<ShapeComponent>> redrawer;

    public RedrawTaskFactory(Supplier<List<ShapeComponent>> allShapesSupplier, Consumer<List<ShapeComponent>> redrawer)
    {
        this.allShapesSupplier = allShapesSupplier;
        this.redrawer = redrawer;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        return new RedrawTask(allShapesSupplier.get(), redrawer);
    }
}
