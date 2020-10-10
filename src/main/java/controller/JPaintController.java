package controller;

import controller.commands.factory.*;
import controller.interfaces.IControllerTask;
import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import controller.suppliersvc.SupplierSvc;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import view.EventName;
import view.ViewAPI;
import view.viewstate.ViewState;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
	private static JPaintController instance;

	//Using Functional interfaces to decouple Controller from Model.
	//private final Supplier<PointInt> pasteLocationSupplier = ModelAPI::getPasteLocation;
	//private final Supplier<List<ShapeComponent>> copyBufferSupplier = ModelAPI::getComponentBuffer;
	//private final Supplier<List<ShapeComponent>> selectionSupplier = ModelAPI::getSelection;
	//private final Supplier<List<ShapeComponent>> allShapesSupplier = ModelAPI::getComponents;
	private final Consumer<IControllerTask> registerCanvasStateSubscriber = ModelAPI::registerCanvasStateSubscriber;
	final Consumer<List<ShapeComponent>> redrawer = ViewAPI::redraw;

	//Initialize object constructors to perform tasks on model based on functional interfaces
	SupplierSvc supplierSvc = SupplierSvc.getInstance();
	private final AbstractTaskFactory copyTaskFactory = new CopyTaskFactory();
	private final AbstractTaskFactory deleteTaskFactory = new DeleteTaskFactory(supplierSvc.getShapeList(SupplierSvc.ShapeListType.SELECTION));
	private final AbstractTaskFactory pasteTaskFactory = new PasteTaskFactory(supplierSvc.getPasteLocation(), supplierSvc.getShapeList(SupplierSvc.ShapeListType.COPY_BUFFER));
	private final AbstractTaskFactory groupTaskFactory = new GroupTaskFactory(supplierSvc.getShapeList(SupplierSvc.ShapeListType.SELECTION));
	private final AbstractTaskFactory ungroupTaskFactory = new UngroupTaskFactory(supplierSvc.getShapeList(SupplierSvc.ShapeListType.SELECTION));
	private final AbstractTaskFactory undoTaskFactory = new UndoTaskFactory();
	private final AbstractTaskFactory redoTaskFactory = new RedoTaskFactory();


	private final AbstractTaskFactory redrawTaskFactory = new RedrawTaskFactory(SupplierSvc.getInstance().getShapeList(SupplierSvc.ShapeListType.ALL_SHAPES), redrawer);

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
	    registerCanvasStateSubscriber.accept(redrawTaskFactory.createTask());
	}
}