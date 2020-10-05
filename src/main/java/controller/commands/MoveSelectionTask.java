package controller.commands;

import model.CommandHistory;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.List;

// Responsible for moving all ShapeComponents in a selection to new coordinates.
public class MoveSelectionTask extends AbstractControllerCommand
{
	private Integer deltaX;
	private Integer deltaY;
	private List<ShapeComponent> selection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public MoveSelectionTask(PointInt startPoint, PointInt endPoint)
	{
		this.deltaX = endPoint.getX() - startPoint.getX();
		this.deltaY = endPoint.getY() - startPoint.getY();
		this.selection = ModelAPI.getSelection();
	}

	// Store relative change to position then move selected components.
	@Override
	public void execute()
	{
		this.move();
		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();
	}

	// The opposite of moving by a delta is moving by a minus delta.
	@Override
	public void undo()
	{
		this.moveBack();
	}

	// Move stored selection.
	@Override
	public void redo()
	{
		this.move();
	}

	// Move selection components by a delta relative point.
	private void move()
	{
		selection.forEach((shapeComponent)->
		{
			var x = shapeComponent.getAnchor().getX() + deltaX;
			var y = shapeComponent.getAnchor().getY() + deltaY;
			ModelAPI.setShapeLocation(shapeComponent, x, y);
		});
	}

	private void moveBack()
	{
		selection.forEach((shapeComponent)->
		{
			var x = shapeComponent.getAnchor().getX() - deltaX;
			var y = shapeComponent.getAnchor().getY() - deltaY;
			ModelAPI.setShapeLocation(shapeComponent, x, y);
		});
	}

}
