package controller;

import controller.commands.RedrawTask;
import controller.interfaces.ICanvasControllerCommand;
import controller.interfaces.ICommand;
import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import model.api.ModelAPI;
import model.persistence.ModelState;
import model.shape.ShapeComponent;
import view.EventName;
import view.Redraw;
import view.viewstate.ViewState;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
	private static JPaintController instance;

	public JPaintController() throws Exception
	{
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
	    ModelAPI.registerOnCanvasNotify(new RedrawTask());
	}


}