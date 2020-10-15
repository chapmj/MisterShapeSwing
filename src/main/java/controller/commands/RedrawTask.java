package controller.commands;

import model.interfaces.IShape;

import java.util.List;
import java.util.function.Consumer;

public class RedrawTask extends AbstractControllerTask
{
    private List<IShape> shapeComponents;
    private Consumer<List<IShape>> redrawer;

    public RedrawTask(List<IShape> shapeComponents, Consumer<List<IShape>> redrawer)
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
