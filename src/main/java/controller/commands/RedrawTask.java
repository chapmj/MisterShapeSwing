package controller.commands;

import model.shape.ShapeComponent;

import java.util.List;
import java.util.function.Consumer;

public class RedrawTask extends AbstractControllerTask
{
    private List<ShapeComponent> shapeComponents;
    private Consumer<List<ShapeComponent>> redrawer;

    public RedrawTask(List<ShapeComponent> shapeComponents, Consumer<List<ShapeComponent>> redrawer)
    {

        this.shapeComponents = shapeComponents;
        this.redrawer = redrawer;
    }

    @Override
    public void execute()
    {
        redrawer.accept(shapeComponents);
    }
}
