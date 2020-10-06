package controller;

import controller.commands.*;
import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import view.EventName;
import view.viewstate.ViewState;

import java.util.List;
import java.util.function.Supplier;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
	private static JPaintController instance;

	Supplier<List<ShapeComponent>> selectionSupplier = ModelAPI::getSelection;
	private final Supplier<PointInt> pasteLocationSupplier = ModelAPI::getPasteLocation;
	private final Supplier<List<ShapeComponent>> copyBufferSupplier = ModelAPI::getComponentBuffer;

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
		ui.addEvent(EventName.COPY,    () -> (new CopyTask()).execute());
		ui.addEvent(EventName.PASTE,   () -> (new PasteTask(pasteLocationSupplier.get(), copyBufferSupplier.get())).execute());
		ui.addEvent(EventName.DELETE,  () -> (new DeleteTask(selectionSupplier.get())).execute());
		ui.addEvent(EventName.GROUP,   () -> (new GroupTask(selectionSupplier.get())).execute());
		ui.addEvent(EventName.UNGROUP, () -> (new UngroupTask(selectionSupplier.get())).execute());
		ui.addEvent(EventName.UNDO,    () -> (new UndoTask()).execute());
		ui.addEvent(EventName.REDO,    () -> (new RedoTask()).execute());
	}

	// Observe CanvasState fields that concern this controller.
	private void registerObservers()
	{
	    ModelAPI.registerOnCanvasNotify(new RedrawTask());
	}


}