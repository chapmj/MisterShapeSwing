package controller.suppliersvc;

import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.List;
import java.util.function.Supplier;

public class CopyBufferSvc
{
    private static final Supplier<List<ShapeComponent>> supplier = ModelAPI::getComponentBuffer;

    public static List<ShapeComponent> get()
    {
        return supplier.get();
    }
}
