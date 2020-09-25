package controller;

import model.PointInt;
import model.persistence.CanvasState;
import model.persistence.MouseReleaseObserver;
import model.persistence.MouseReleaseSubject;
import view.MouseHandler;

/* Controller for mouse relate events
 * SINGLETON PATTERN
 * OBSERVER PATTERN
 */
public class MouseController {

	private static MouseController instance;
	private static MouseReleaseSubject mouseReleaseSubject;
	private static MouseReleaseObserver mouseReleaseObserver;
	private static JPaintController controller;
	private static CanvasController canvasController;
	private static CanvasState canvasState;

	private MouseController() {
		canvasController = CanvasController.getInstance();	
		controller = JPaintController.getInstance();
		canvasState = canvasController.getCanvasState();
	}
	
	private static void postInstance() {
		mouseReleaseSubject = new MouseReleaseSubject(); 
		mouseReleaseObserver = new MouseReleaseObserver();
		mouseReleaseSubject.registerObserver(mouseReleaseObserver);
		registerMouse();
	}

	// Singleton creation code satisfies ISingleton needs.
	public static MouseController getInstance() {
		return instance;
	}
	
	public static void createInstance() {
		instance = new MouseController();
		postInstance();
	}
	
	// Assign an observer to watch CanvasState for mouse related state changes.
	private static void registerMouse() {
        canvasController.getPaintCanvas().addMouseListener(new MouseHandler());
	}

	// Runs a command depending on application mode when the mouse button is unpressed.
	public void mouseReleaseAction() throws Exception {
		PointInt startPoint = getPress();
		PointInt endPoint = getRelease();
		if (startPoint != null && endPoint != null) {
			switch(controller.getStartAndEndPointMode()) {
				case DRAW: 
					CanvasControllerCommands.draw(startPoint, endPoint);
					break;
				case SELECT: 
					CanvasControllerCommands.saveSelection(startPoint, endPoint);
					break;
				case MOVE: 
					CanvasControllerCommands.moveSelection(startPoint, endPoint);
					break;
			}
		}
	}

	// CanvasState interactions
	public PointInt getPress() {
		return canvasState.getMousePressed();	
	}

	public PointInt getRelease() throws Exception {
		return canvasState.getMouseReleased();	
	}

	public void setPress(int x, int y) {
		canvasState.setMousePressed(new PointInt(x,y));	
	}

	public void setRelease(int x, int y) throws Exception {
		canvasState.setMouseReleased(new PointInt(x,y));	
		mouseReleaseSubject.notifyObservers();
	}
}