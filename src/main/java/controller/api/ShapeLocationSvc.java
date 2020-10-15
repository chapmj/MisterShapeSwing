package controller.api;

import model.PointInt;
import model.api.ModelAPI;
import model.interfaces.IShape;

import java.util.function.BiConsumer;

public class ShapeLocationSvc
{
    private static final BiConsumer<IShape, PointInt> consumer = ModelAPI::setShapeLocation;

    public static void accept(IShape shapeComponent, PointInt point)
    {
        consumer.accept(shapeComponent, point);
    }
}
