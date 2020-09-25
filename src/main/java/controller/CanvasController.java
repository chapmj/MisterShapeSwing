package controller;

import java.awt.Graphics2D;
import java.util.ArrayList;

import controller.interfaces.ICanvasController;
import controller.interfaces.ISingleton;
import model.ShapeGroup;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;
import model.persistence.CanvasStateObserver;
import model.persistence.CanvasStateSubject;
import view.EventName;
import view.Redraw;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;


/* CanvasController is responsible for managing data that updates the
 * canvas workspace in the application. This acts as a middle-man for
 * interacting with CanvasState model data.  It also has its own
 * subject/observer which updates the application's view whenever
 * CanvasState changes are made.
 * SIGNLETON PATTERN
 * OBSERVER PATTERN
 */

public class CanvasController implements ICanvasController, ISingleton {
	private static CanvasController instance; 
	private PaintCanvasBase paintCanvas;
	private JPaintController controller;
	private CanvasStateObserver canvasStateObserver;
	CanvasState canvasState;
	CanvasStateSubject canvasStateSubject;

	private CanvasController (PaintCanvasBase paintCanvas) {
		this.paintCanvas = paintCanvas;
	}
	
	// Singleton creation code satisfies ISingleton needs.
	public static CanvasController getInstance() {
		if (instance == null) 
			return null;	
		else 
			return instance;
	}
	
	public static void createInstance(PaintCanvasBase paintCanvas) {
		if (instance == null) {
			instance = new CanvasController(paintCanvas);
			instance.createInstancePost();
		}
	}
	
	// Setup code to run after the singleton object is created.
	private void createInstancePost() {
		controller = JPaintController.getInstance();
		canvasState = new CanvasState(controller);
		canvasStateSubject = new CanvasStateSubject(); 
		canvasStateObserver = new CanvasStateObserver(instance, canvasStateSubject);
		CanvasControllerCommands.initAfterCanvasStateSubject(); //force static fields to load
	}


	// Setup code for application behavior.
	@Override
	public void setup () throws Exception {
		registerEvents();
		registerObservers();
	}
	
	private void registerEvents() {
		IUiModule uiModule = controller.getUiModule();
		uiModule.addEvent(EventName.COPY, () -> CanvasControllerCommands.copySelection());
		uiModule.addEvent(EventName.PASTE, () -> CanvasControllerCommands.pasteSelection());
		uiModule.addEvent(EventName.DELETE, () -> CanvasControllerCommands.deleteSelection());
		uiModule.addEvent(EventName.GROUP, () -> CanvasControllerCommands.groupSelection());
		uiModule.addEvent(EventName.UNGROUP, () -> CanvasControllerCommands.ungroupSelection());
		uiModule.addEvent(EventName.UNDO, () -> CanvasControllerCommands.undo());
		uiModule.addEvent(EventName.REDO, () -> CanvasControllerCommands.redo());
	}

	// Observe CanvasState fields that concern this controller.
	private void registerObservers() throws Exception {
		canvasStateSubject.registerObserver(canvasStateObserver);
	}

	// Update application view to match model state (CanvasState)
	public void redraw() throws Exception {
		Redraw.execute(getShapeComponents());
	}

	// Controller fields of used by CanvasController commands.
	public Graphics2D getGraphics2D() {
		return paintCanvas.getGraphics2D();
	}

	public PaintCanvasBase getPaintCanvas() {
		return paintCanvas;
	}

	// Canvas State read fields.

	public CanvasStateSubject getCanvasStateSubject() {
		return canvasStateSubject;
	}
	
	public ArrayList<IShape> getShapeList() {
		return canvasState.getShapeList();
	}

	public ArrayList<ShapeComponent> getShapeComponentSelectionList() {
		return canvasState.getComponentSelectionList();	
	}

	public ArrayList<ShapeGroup> getShapeGroups() {
		return canvasState.getShapeGroups();
	}

	public CanvasState getCanvasState() {
		return this.canvasState;
	}
	
	public ArrayList<ShapeComponent> getShapeComponents() {
		return canvasState.getComponentList();
	}

	// Canvas State write fields

	public void addShapeGroup(ShapeGroup group) throws Exception {
		canvasState.addShapeGroup(group);
	}
	
	public void removeShapeGroup(ShapeGroup group) throws Exception {
		canvasState.getShapeGroups().remove(group);
	}

	public ArrayList<IShape> getShapeSelectionList() {
		return canvasState.getShapeSelectionList();
	}

	public void removeComponents(ArrayList<ShapeComponent> selection) {
		canvasState.removeComponent(selection);
	}
	public void removeComponent(ShapeComponent shapeComponent) {
		canvasState.removeComponent(shapeComponent);
	}

	public void addComponents(ArrayList<ShapeComponent> shapeComponents) {
		canvasState.addComponent(shapeComponents);	
	}

	public void addComponent(ShapeComponent shapeComponent) {
		ArrayList<ShapeComponent> components = new ArrayList<>();
		components.add(shapeComponent);
		addComponents(components);	
	}
}