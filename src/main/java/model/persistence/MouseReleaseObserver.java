package model.persistence;

import controller.MouseController;
import model.interfaces.IObservable;

// Watch for when mouse release state data is updated.
public class MouseReleaseObserver implements IObservable
{

	private MouseController mouseController;

	public MouseReleaseObserver() {
		mouseController = MouseController.getInstance();
	}

	@Override
	public void update()
	{
		try
		{
			var pressed = ModelState.getCanvasState().getMousePressed();
			var released = ModelState.getCanvasState().getMouseReleased();
			mouseController.mouseReleaseAction(pressed, released);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
