package controller.commands;

import controller.CanvasUtils;
import model.PointInt;
import model.shape.ShapeComponent;
import model.persistence.CanvasState;
import model.persistence.ModelState;

import java.util.ArrayList;
import java.util.List;

// Responsible for moving all ShapeComponents in a selection to new coordinates.
public class MoveSelectionTask extends AbstractControllerCommand
{
	private PointInt startPoint;
	private PointInt endPoint;
	private Integer deltaX;
	private Integer deltaY;
	private List<ShapeComponent> selection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public MoveSelectionTask(PointInt startPoint, PointInt endPoint)
	{
		CanvasState canvasState = ModelState.getCanvasState();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.selection = new ArrayList<>(canvasState.getComponentSelectionList());
	}

	// Store relative change to position then move selected components.
	@Override
	public void execute()
	{
		setDelta();
		move();
	}

	// The opposite of moving by a delta is moving by a minus delta.
	@Override
	public void undo()
	{
		CanvasUtils.moveShapes(selection, new PointInt(-deltaX, -deltaY));
	}

	// Move stored selection.
	@Override
	public void redo()
	{
		move();
	}

	// Move selection components by a delta relative point.
	private void move()
	{
		CanvasUtils.moveShapes(selection, new PointInt(deltaX, deltaY));
	}
	
	private void setDelta()
	{
		deltaX = endPoint.getX() - startPoint.getX();
		deltaY = endPoint.getY() - startPoint.getY();
	}	
}
