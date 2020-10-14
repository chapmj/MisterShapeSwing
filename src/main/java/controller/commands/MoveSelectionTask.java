package controller.commands;

import controller.api.ShapeLocationSvc;
import model.CommandHistory;
import model.PointInt;
import model.api.ModelAPI;
import model.interfaces.IShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Responsible for moving all ShapeComponents in a selection to new coordinates.
public class MoveSelectionTask extends AbstractControllerTask
{
	private final Integer deltaX;
	private final Integer deltaY;
	private final List<IShape> shapes;

	@SuppressWarnings("unused")
	private MoveSelectionTask() throws Exception
	{
		throw new Exception("MoveSelectionTask must be parameterized");
	}

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public MoveSelectionTask(PointInt startPoint, PointInt endPoint, List<IShape> selection)
	{
		this.deltaX = endPoint.getX() - startPoint.getX();
		this.deltaY = endPoint.getY() - startPoint.getY();
		this.shapes = selection;
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
		shapes.stream()
			.map((shapeComponent) ->
			{
				var x = shapeComponent.getAnchor().getX();
				var y = shapeComponent.getAnchor().getY();
				var position = new PointInt(x + deltaX, y + deltaY);

				return Map.of(shapeComponent, position);
			})
			.forEach((entry) -> entry.forEach(ShapeLocationSvc::accept));
	}

	private void moveBack()
	{
		shapes.stream()
			.map((shapeComponent) ->
			{
				var x = shapeComponent.getAnchor().getX();
				var y = shapeComponent.getAnchor().getY();
				var position = new PointInt(x - deltaX, y - deltaY);

				return Map.of(shapeComponent, position);
			})
			.forEach((entry) -> entry.forEach(ShapeLocationSvc::accept));
	}
}
