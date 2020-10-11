package controller.api;

import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.function.BiConsumer;

public class ShapeLocationSvc
{
    private static final BiConsumer<ShapeComponent, PointInt> consumer = ModelAPI::setShapeLocation;

    public static void accept(ShapeComponent shapeComponent, PointInt point)
    {
        consumer.accept(shapeComponent, point);
    }
}
