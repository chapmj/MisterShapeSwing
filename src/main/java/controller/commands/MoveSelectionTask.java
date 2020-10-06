package controller.commands;

import model.CommandHistory;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

// Responsible for moving all ShapeComponents in a selection to new coordinates.
public class MoveSelectionTask extends AbstractControllerCommand
{
	private final Integer deltaX;
	private final Integer deltaY;
	private final List<ShapeComponent> shapes;
	private final BiConsumer<ShapeComponent, PointInt> moveShapeInModel = ModelAPI::setShapeLocation;

	@SuppressWarnings("unused")
	private MoveSelectionTask() throws Exception
	{
		throw new Exception("DeleteTask must be parameterized");
	}

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public MoveSelectionTask(PointInt startPoint, PointInt endPoint, List<ShapeComponent> selection)
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
			.map((shapeComponent) -> Map.of(
				shapeComponent,
				new PointInt(
					shapeComponent.getAnchor().getX() + deltaX,
					shapeComponent.getAnchor().getY() + deltaY)))
			.forEach((entry) -> entry.forEach(moveShapeInModel));
	}

	private void moveBack()
	{
		shapes.stream()
			.map((shapeComponent) -> Map.of(
					shapeComponent,
					new PointInt(
							shapeComponent.getAnchor().getX() - deltaX,
							shapeComponent.getAnchor().getY() - deltaY)))
			.forEach((entry) -> entry.forEach(moveShapeInModel));
	}
}
