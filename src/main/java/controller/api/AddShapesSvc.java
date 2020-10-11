package controller.api;

import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.function.Consumer;

public class AddShapesSvc
{
    private static final Consumer<List<ShapeComponent>> shapeListConsumer = ModelAPI::addShapes;
    private static final Consumer<ShapeGroup> shapeGroupConsumer = ModelAPI::addShapeGroup;
    private static final Consumer<ShapeComponent> shapeComponentConsumer = ModelAPI::addShape;

    public static void accept(List<ShapeComponent> shapes)
    {
        shapeListConsumer.accept(shapes);
    }

    public static void accept(ShapeGroup shapeGroup)
    {
        shapeGroupConsumer.accept(shapeGroup);
    }

    public static void accept(ShapeComponent shapeComponent)
    {
        shapeComponentConsumer.accept(shapeComponent);
    }
}
