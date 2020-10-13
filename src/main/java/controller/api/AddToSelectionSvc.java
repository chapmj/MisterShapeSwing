package controller.api;

import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.function.Consumer;

public class AddToSelectionSvc
{
    private static final Consumer<ShapeGroup> shapeGroupConsumer = ModelAPI::addComponentToSelection;
    private static final Consumer<List<IShape>> shapeListConsumer = ModelAPI::addComponentToSelection;

    public static void accept(ShapeGroup shapeGroup)
    {
        shapeGroupConsumer.accept(shapeGroup);
    }

    public static void accept(List<IShape> shapeComponents)
    {
        shapeListConsumer.accept(shapeComponents);
    }

}
