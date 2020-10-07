package controller.commands;

import controller.CanvasUtils;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapePosition;

import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class SaveSelectionTask extends AbstractControllerTask
{
	private List<ShapeComponent> selection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public SaveSelectionTask(PointInt startPoint, PointInt endPoint, List<ShapeComponent> components)
	{
		var selectionBox = new ShapePosition(startPoint, endPoint);

		selection = components.stream()
				.filter ((component) -> CanvasUtils.compareRects(component.getPosition(), selectionBox))
				.collect (Collectors.toList());
	}

	/* Create a selection by storing components bounded by the mouse in a selection
	 * list stored in model.
	 */
	public void execute()
	{
		ModelAPI.clearSelection();
		ModelAPI.addComponentToSelection(selection);
		ModelAPI.notifyCanvasObservers();
	}



}