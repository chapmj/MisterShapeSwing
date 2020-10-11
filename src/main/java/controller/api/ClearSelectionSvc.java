package controller.api;

import model.api.ModelAPI;

import java.util.function.Consumer;

public class ClearSelectionSvc
{
    public static void apply()
    {
        ModelAPI.clearSelection();
    }

}
