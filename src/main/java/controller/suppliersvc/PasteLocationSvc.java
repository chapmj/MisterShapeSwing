package controller.suppliersvc;

import model.PointInt;
import model.api.ModelAPI;

import java.util.function.Supplier;

public class PasteLocationSvc
{
    private static final Supplier<PointInt> supplier = ModelAPI::getPasteLocation;

    public static PointInt get()
    {
        return supplier.get();
    }
}
