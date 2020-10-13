package controller;

import controller.commands.factory.*;
import controller.interfaces.IControllerTask;
import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import controller.api.CanvasShapesSvc;
import controller.api.CopyBufferSvc;
import controller.api.PasteLocationSvc;
import controller.api.SelectionSvc;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import view.EventName;
import view.ViewAPI;

import java.util.List;
import java.util.function.Consumer;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
	private static JPaintController instance;

	private final Consumer<IControllerTask> registerCanvasStateSubscriber = ModelAPI::registerCanvasStateSubscriber;
	final Consumer<List<IShape>> redrawer = ViewAPI::redraw;

	//Initialize object constructors to perform tasks on model based on functional interfaces
	private final AbstractTaskFactory copyTaskFactory = new CopyTaskFactory();
	private final AbstractTaskFactory deleteTaskFactory = new DeleteTaskFactory(SelectionSvc.getSupplier());
	private final AbstractTaskFactory pasteTaskFactory = new PasteTaskFactory(PasteLocationSvc.getSupplier(), CopyBufferSvc.getSupplier());
	private final AbstractTaskFactory groupTaskFactory = new GroupTaskFactory(SelectionSvc.getSupplier());
	private final AbstractTaskFactory ungroupTaskFactory = new UngroupTaskFactory(SelectionSvc.getSupplier());
	private final AbstractTaskFactory undoTaskFactory = new UndoTaskFactory();
	private final AbstractTaskFactory redoTaskFactory = new RedoTaskFactory();


	private final AbstractTaskFactory redrawTaskFactory = new RedrawTaskFactory(CanvasShapesSvc.getSupplier(), redrawer);

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
		ViewAPI.addGuiEvent(EventName.COPY,    () -> copyTaskFactory.createTask().execute());
		ViewAPI.addGuiEvent(EventName.PASTE,   () -> pasteTaskFactory.createTask().execute());
		ViewAPI.addGuiEvent(EventName.DELETE,  () -> deleteTaskFactory.createTask().execute());
		ViewAPI.addGuiEvent(EventName.GROUP,   () -> groupTaskFactory.createTask().execute());
		ViewAPI.addGuiEvent(EventName.UNGROUP, () -> ungroupTaskFactory.createTask().execute());
		ViewAPI.addGuiEvent(EventName.UNDO,    () -> undoTaskFactory.createTask().execute());
		ViewAPI.addGuiEvent(EventName.REDO,    () -> redoTaskFactory.createTask().execute());
	}

	// Observe CanvasState fields that concern this controller.
	private void registerObservers()
	{
	    registerCanvasStateSubscriber.accept(redrawTaskFactory.createTask());
	}
}