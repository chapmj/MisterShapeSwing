package controller.commands;

import controller.CanvasUtils;
import model.PointInt;
import model.shape.ShapeComponent;
import model.persistence.CanvasState;
import model.persistence.ModelState;
import model.shape.ShapePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class SaveSelectionTask extends AbstractControllerCommand
{
	private CanvasState canvasState;
	private List<ShapeComponent> components;
	private ShapePosition selectionBox;
	private List<ShapeComponent> componentSelection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public SaveSelectionTask(PointInt startPoint, PointInt endPoint)
	{
		this.canvasState = ModelState.getCanvasState();
		this.components = canvasState.getComponentList();
		this.selectionBox = new ShapePosition(startPoint, endPoint);
	}
	
	public void execute()
	{
		addComponentsToSelection();
	}

	/* Create a selection by storing components bounded by the mouse in a selection
	 * list stored in model.
	 */ 
	private void addComponentsToSelection()
	{
		addOverlappingShapeComponents(components, selectionBox);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(componentSelection);
	}

	// Create a selection of objects that intersect with the bounds of mouse press and release.
	private void addOverlappingShapeComponents(List<ShapeComponent> components, ShapePosition selectionBox)
	{
		/*
		componentSelection = new ArrayList<>();
		componentSelection.addAll(components.stream()
			.filter ((component) -> CanvasUtils.compareRects(component.getPosition(), selectionBox))
			.collect (Collectors.toList()));

		 */
		componentSelection = components.stream()
				.filter ((component) -> CanvasUtils.compareRects(component.getPosition(), selectionBox))
				.collect (Collectors.toList());
	}
}