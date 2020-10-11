package controller;

import controller.commands.DrawTask;
import controller.commands.MoveSelectionTask;
import controller.commands.SaveSelectionTask;
import controller.api.CanvasShapesSvc;
import controller.api.SelectionSvc;
import model.PointInt;
import model.persistence.ModelState;
import model.persistence.MouseReleaseObserver;
import model.persistence.MouseReleaseSubject;
import view.viewstate.ViewState;

/* Controller for mouse relate events
 * SINGLETON PATTERN
 * OBSERVER PATTERN
 */
public class MouseController {

	private static MouseController instance;
	private static MouseReleaseSubject mouseReleaseSubject;
	private static MouseReleaseObserver mouseReleaseObserver;

	private MouseController() { }
	
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
        ViewState.getCanvas().addMouseListener(new MouseHandler());
	}

	// Runs a command depending on application mode when the mouse button is unpressed.
	public void mouseReleaseAction() throws Exception {
		PointInt startPoint = getPress();
		PointInt endPoint = getRelease();
		if (startPoint != null && endPoint != null) {
			switch(ModelState.getApplicationState().getStartAndEndPointMode()) {
				case DRAW: 
					var shapeType = ModelState.getApplicationState().getShapeType();
					var shapeStyle = ModelState.getApplicationState().getShapeStyle();

					var draw = new DrawTask(startPoint, endPoint, shapeType, shapeStyle);
					draw.execute();

					break;

				case SELECT:
					var allShapes = CanvasShapesSvc.get();
					var saveSelection = new SaveSelectionTask(startPoint, endPoint, allShapes);
					saveSelection.execute();
					break;

				case MOVE: 
					var selection = SelectionSvc.get();
					var move = new MoveSelectionTask(startPoint, endPoint, selection);
					move.execute();
					break;

				default:
				    //log that release mode is not implemented
					//throw new Exception("Release Mode not implemented");

			}
		}
	}

	// CanvasState interactions
	// Todo create mouse input service
	public PointInt getPress()
	{
		return ModelState.getCanvasState().getMousePressed();
	}

	public PointInt getRelease()
	{
		return ModelState.getCanvasState().getMouseReleased();
	}

	public void setPress(int x, int y)
	{
		ModelState.getCanvasState().setMousePressed(new PointInt(x,y));
	}

	public void setRelease(int x, int y) throws Exception {
		ModelState.getCanvasState().setMouseReleased(new PointInt(x,y));
		mouseReleaseSubject.notifyObservers();
	}
}