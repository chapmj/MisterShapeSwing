package controller.api;

import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.function.Consumer;

public class RemoveShapeSvc
{
    private static final Consumer<List<ShapeComponent>> shapeListConsumer = ModelAPI::removeShapes;
    private static final Consumer<ShapeGroup> shapeGroupConsumer = ModelAPI::removeShape;
    private static final Consumer<ShapeComponent> shapeComponentConsumer = ModelAPI::removeShape;

    public static void accept(List<ShapeComponent> shapes)
    {
        shapeListConsumer.accept(shapes);
    }

    public static void accept(ShapeComponent shapeComponent)
    {
        shapeComponentConsumer.accept(shapeComponent);
    }

    public static void accept(ShapeGroup shapeGroup)
    {
        shapeGroupConsumer.accept(shapeGroup);
    }
}
