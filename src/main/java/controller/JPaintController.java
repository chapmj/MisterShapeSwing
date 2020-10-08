package controller;

import controller.commands.factory.*;
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

	private final Supplier<PointInt> pasteLocationSupplier = ModelAPI::getPasteLocation;
	private final Supplier<List<ShapeComponent>> copyBufferSupplier = ModelAPI::getComponentBuffer;
	private final Supplier<List<ShapeComponent>> selectionSupplier = ModelAPI::getSelection;
	private final Supplier<List<ShapeComponent>> allShapesSupplier = ModelAPI::getComponents;

	private final AbstractTaskFactory copyTaskFactory = new CopyTaskFactory();
	private final AbstractTaskFactory deleteTaskFactory = new DeleteTaskFactory(selectionSupplier);
	private final AbstractTaskFactory pasteTaskFactory = new PasteTaskFactory(pasteLocationSupplier, copyBufferSupplier);
	private final AbstractTaskFactory groupTaskFactory = new GroupTaskFactory(selectionSupplier);
	private final AbstractTaskFactory ungroupTaskFactory = new UngroupTaskFactory(selectionSupplier);
	private final AbstractTaskFactory undoTaskFactory = new UndoTaskFactory();
	private final AbstractTaskFactory redoTaskFactory = new RedoTaskFactory();
	private final AbstractTaskFactory redrawTaskFactory = new RedrawTaskFactory(allShapesSupplier);

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
		ui.addEvent(EventName.COPY,    () -> copyTaskFactory.createTask().execute());
		ui.addEvent(EventName.PASTE,   () -> pasteTaskFactory.createTask().execute());
		ui.addEvent(EventName.DELETE,  () -> deleteTaskFactory.createTask().execute());
		ui.addEvent(EventName.GROUP,   () -> groupTaskFactory.createTask().execute());
		ui.addEvent(EventName.UNGROUP, () -> ungroupTaskFactory.createTask().execute());
		ui.addEvent(EventName.UNDO,    () -> undoTaskFactory.createTask().execute());
		ui.addEvent(EventName.REDO,    () -> redoTaskFactory.createTask().execute());
	}

	// Observe CanvasState fields that concern this controller.
	private void registerObservers()
	{
	    ModelAPI.registerCanvasStateSubscriber(redrawTaskFactory.createTask());
	}
}