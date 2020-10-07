package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;

public class RedoTask extends AbstractControllerTask
{
	@Override
	public void execute()
	{
		try
		{
			CommandHistory.redo();
			ModelAPI.notifyCanvasObservers();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
