package controller.api;

import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.List;
import java.util.function.Supplier;

public class SelectionSvc
{
    private static final Supplier<List<ShapeComponent>> supplier = ModelAPI::getSelection;

    public static List<ShapeComponent> get()
    {
        return supplier.get();
    }

    public static Supplier<List<ShapeComponent>> getSupplier()
    {
        return supplier;
    }
}