package model.persistence;

import controller.interfaces.ICanvasControllerCommand;
import model.interfaces.IObservable;

/* Observes the canvasState object for changes
 */
public class CanvasStateObserver implements IObservable
{
	ICanvasControllerCommand command;

	private CanvasStateObserver() {
	}

	public CanvasStateObserver(ICanvasControllerCommand command)
	{
		this.command = command;
	}

	/* When data stored on the canvas changes, redraw the canvas to
 * keep the canvas view in sync with the model data
 */
	@Override
	public void update()
	{
		try
		{
			command.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
