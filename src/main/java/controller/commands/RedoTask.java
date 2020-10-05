package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;

public class RedoTask extends AbstractControllerCommand
{
	@Override
	public void execute()
	{
		try
		{
			CommandHistory.redo();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		ModelAPI.notifyCanvasObservers();
	}
}
