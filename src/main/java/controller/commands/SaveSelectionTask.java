package controller.commands;

import controller.api.AddToSelectionSvc;
import controller.api.ClearSelectionSvc;
import model.PointInt;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapePosition;

import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class SaveSelectionTask extends AbstractControllerTask
{
	private final List<IShape> selection;
	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public SaveSelectionTask(PointInt startPoint, PointInt endPoint, List<IShape> components)
	{
		var selectionBox = new ShapePosition(startPoint, endPoint);

		selection = components.stream()
				.filter ((component) -> compareRects(component.getPosition(), selectionBox))
				.collect (Collectors.toList());
	}

	/* Create a selection by storing components bounded by the mouse in a selection
	 * list stored in model.
	 */
	public void execute()
	{
		ClearSelectionSvc.apply();
		AddToSelectionSvc.accept(selection);
		ModelAPI.notifyCanvasObservers();
	}

	private boolean compareRects(ShapePosition pos1, ShapePosition pos2)
	{
		int r1TopY = pos1.getLeft().getY();
		int r1BotY = pos1.getRight().getY();
		int r2TopY = pos2.getLeft().getY();
		int r2BotY = pos2.getRight().getY();

		int r1LeftX = pos1.getLeft().getX();
		int r1RightX = pos1.getRight().getX();
		int r2LeftX = pos2.getLeft().getX();
		int r2RightX = pos2.getRight().getX();

		return !((r1LeftX > r2RightX)
				|| (r1RightX < r2LeftX)
				|| (r1TopY > r2BotY)
				|| (r1BotY < r2TopY));
	}
}