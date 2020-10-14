package controller.api;

import model.PointInt;
import model.api.ModelAPI;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PasteLocationSvc
{
    private static final Supplier<PointInt> supplier = ModelAPI::getPasteLocation;
    private static final Consumer<PointInt> consumer = ModelAPI::setPasteLocation;

    public static PointInt get()
    {
        return supplier.get();
    }

    public static Supplier<PointInt> getSupplier()
    {
        return supplier;
    }

    public static void accept(PointInt point)
    {
        consumer.accept(point);
    }
}
