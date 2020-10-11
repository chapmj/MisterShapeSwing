package controller.api;

import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CopyBufferSvc
{
    private static final Supplier<List<ShapeComponent>> supplier = ModelAPI::getComponentBuffer;
    private static final Consumer<List<ShapeComponent>> consumer = ModelAPI::putComponentBuffer;

    public static List<ShapeComponent> get()
    {
        return supplier.get();
    }

    public static Supplier<List<ShapeComponent>> getSupplier()
    {
        return supplier;
    }

    public static void put(List<ShapeComponent> shapeComponents)
    {
        var shapeCopies = new ArrayList<>(shapeComponents);
        consumer.accept(shapeCopies);
    }
}
