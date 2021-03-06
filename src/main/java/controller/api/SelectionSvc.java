package controller.api;

import model.api.ModelAPI;
import model.interfaces.IShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SelectionSvc
{
    private static final Supplier<List<IShape>> supplier = ModelAPI::getSelection;

    public static List<IShape> get()
    {
        return new ArrayList<>(supplier.get());
    }

    public static Supplier<List<IShape>> getSupplier()
    {
        return supplier;
    }
}
