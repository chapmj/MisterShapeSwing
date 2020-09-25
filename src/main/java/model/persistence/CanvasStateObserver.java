package model.persistence;

import controller.CanvasController;
import model.interfaces.IObservableObserver;

/* Observes the canvasState object for changes
 */
public class CanvasStateObserver implements IObservableObserver {
	CanvasController canvasController;
	CanvasStateSubject subject;

	public CanvasStateObserver(CanvasController canvasController, CanvasStateSubject subject) {
		this.canvasController= canvasController;
		this.subject = subject;
	}

/* When data stored on the canvas changes, redraw the canvas to
 * keep the canvas view in sync with the model data
 */
	@Override
	public void update() throws Exception {
		canvasController.redraw();
	}

}
