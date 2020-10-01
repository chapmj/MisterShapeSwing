package controller;

import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import model.persistence.CanvasStateObserver;
import model.persistence.ModelState;
import view.EventName;
import view.Redraw;
import view.viewstate.ViewState;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
	private static JPaintController instance;
	CanvasStateObserver canvasStateObserver;

	public JPaintController() throws Exception
	{
		canvasStateObserver = new CanvasStateObserver(ModelState.getCanvasStateSubject());
		CanvasControllerCommands.initAfterCanvasStateSubject(); //force static fields to load
		registerEvents();
		registerObservers();
	}

	public static JPaintController getInstance()
	{
		if (instance == null) 
			return null;	
		else 
			return instance;
	}
	
	public static void createInstance() throws Exception
	{
		if (instance == null)
		{
			instance = new JPaintController();
		}
	}

	public void redraw() throws Exception {
		var shapeComponents = ModelState.getCanvasState().getComponentList();
		Redraw.execute(shapeComponents);
	}

	private void registerEvents() throws Exception
	{
		var ui = ViewState.getUI();
		ui.addEvent(EventName.COPY, CanvasControllerCommands::copySelection);
		ui.addEvent(EventName.PASTE, CanvasControllerCommands::pasteSelection);
		ui.addEvent(EventName.DELETE, CanvasControllerCommands::deleteSelection);
		ui.addEvent(EventName.GROUP, CanvasControllerCommands::groupSelection);
		ui.addEvent(EventName.UNGROUP, CanvasControllerCommands::ungroupSelection);
		ui.addEvent(EventName.UNDO, CanvasControllerCommands::undo);
		ui.addEvent(EventName.REDO, CanvasControllerCommands::redo);
	}

	// Observe CanvasState fields that concern this controller.
	private void registerObservers()
	{
		ModelState.getCanvasStateSubject().registerObserver(canvasStateObserver);
	}

}