package controller.suppliersvc;

import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class SupplierSvc
{
    private final EnumMap<ShapeListType, Supplier<List<ShapeComponent>>> suppliers = new EnumMap<>(ShapeListType.class);
    private Supplier<PointInt> pasteLocation;

    private static SupplierSvc instance;

    public enum ShapeListType
    {
        SELECTION, COPY_BUFFER, ALL_SHAPES
    }

    private static void load()
    {
        getInstance().pasteLocation = ModelAPI::getPasteLocation;

        getInstance().suppliers.put(ShapeListType.COPY_BUFFER, ModelAPI::getComponentBuffer);

        getInstance().suppliers.put(ShapeListType.SELECTION, ModelAPI::getSelection);

        getInstance().suppliers.put(ShapeListType.ALL_SHAPES, ModelAPI::getComponents);
    }

    public SupplierSvc()
    {
    }

    public static SupplierSvc getInstance()
    {
        if (instance == null)
        {
            SupplierSvc.instance = new SupplierSvc();
            load();
        }

        return SupplierSvc.instance;
    }

    public Supplier<List<ShapeComponent>> getShapeList(ShapeListType shapeType)
    {
        return suppliers.get(shapeType);
    }

    public Supplier<PointInt> getPasteLocation()
    {
        return this.pasteLocation;
    }
}
