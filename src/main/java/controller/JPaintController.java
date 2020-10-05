package controller;

import controller.commands.*;
import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import model.api.ModelAPI;
import view.EventName;
import view.viewstate.ViewState;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
	private static JPaintController instance;

	public JPaintController() throws Exception
	{
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
		ui.addEvent(EventName.COPY, () -> (new CopyTask()).execute());
		ui.addEvent(EventName.PASTE, () -> (new PasteTask()).execute());
		ui.addEvent(EventName.DELETE, () -> (new DeleteTask()).execute());
		ui.addEvent(EventName.GROUP, ()-> (new GroupTask()).execute());
		ui.addEvent(EventName.UNGROUP, () -> (new UngroupTask()).execute());
		ui.addEvent(EventName.UNDO, () -> (new UndoTask()).execute());
		ui.addEvent(EventName.REDO, () -> (new RedoTask()).execute());
	}

	// Observe CanvasState fields that concern this controller.
	private void registerObservers()
	{
	    ModelAPI.registerOnCanvasNotify(new RedrawTask());
	}


}