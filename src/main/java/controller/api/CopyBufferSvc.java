package controller.api;

import model.api.ModelAPI;
import model.interfaces.IShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CopyBufferSvc
{
    private static final Supplier<List<IShape>> supplier = ModelAPI::getComponentBuffer;
    private static final Consumer<List<IShape>> consumer = ModelAPI::putComponentBuffer;

    public static List<IShape> get()
    {
        return supplier.get();
    }

    public static Supplier<List<IShape>> getSupplier()
    {
        return supplier;
    }

    public static void put(List<IShape> shapeComponents)
    {
        var shapeCopies = new ArrayList<>(shapeComponents);
        consumer.accept(shapeCopies);
    }
}
