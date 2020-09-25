package controller.commands;

import java.util.ArrayList;
import java.util.stream.Collectors;

import controller.CanvasController;
import controller.CanvasUtils;
//import model.Dimensions;
import model.PointInt;
import model.ShapePosition;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class SaveSelectionTask {
	private CanvasState canvasState;
	private CanvasController canvasController = CanvasController.getInstance();
	private ArrayList <ShapeComponent> components;
	private ShapePosition selectionBox;
	private ArrayList<ShapeComponent> componentSelection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public SaveSelectionTask(PointInt startPoint, PointInt endPoint) {
		this.canvasState = canvasController.getCanvasState();
		this.components = canvasState.getComponentList();
		this.selectionBox = new ShapePosition(startPoint, endPoint);
	}
	
	public void execute() throws Exception {
		addComponentsToSelection();
	}

	/* Create a selection by storing components bounded by the mouse in a selection
	 * list stored in model.
	 */ 
	private void addComponentsToSelection() throws Exception {
		addOverlappingShapeComponents(components, selectionBox);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(componentSelection);
	}

	// Create a selection of objects that intersect with the bounds of mouse press and release.
	private void addOverlappingShapeComponents(ArrayList<ShapeComponent> components, ShapePosition selectionBox) 
	{
		componentSelection = new ArrayList<>();
		componentSelection.addAll(components.stream()
			.filter ((component) -> CanvasUtils.compareRects(component.getPosition(), selectionBox))
			.collect (Collectors.toList()));
	}
}