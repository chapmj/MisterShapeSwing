package model.persistence;

import controller.JPaintController;
import model.interfaces.IObservable;

import static controller.JPaintController.*;

/* Observes the canvasState object for changes
 */
public class CanvasStateObserver implements IObservable
{
	CanvasStateSubject subject;

	public CanvasStateObserver(CanvasStateSubject subject) {
		//this.canvasController= canvasController;
		this.subject = subject;
	}

/* When data stored on the canvas changes, redraw the canvas to
 * keep the canvas view in sync with the model data
 */
	@Override
	public void update()
	{
        try
		{
			JPaintController.getInstance().redraw();
		}
        catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
