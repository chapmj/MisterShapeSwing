package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;

public class UndoTask extends AbstractControllerTask
{
    @Override
    public void execute()
    {
        try {
            CommandHistory.undo();
            ModelAPI.notifyCanvasObservers();
        } catch (Exception exception) {exception.printStackTrace();}

    }
}
