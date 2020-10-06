package controller.commands;

import model.CommandHistory;
import model.Dimensions;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.*;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas.
 */
public class DrawTask extends AbstractControllerCommand
{
	private final ShapeComponent shape;

	@SuppressWarnings("unused")
	private DrawTask() throws Exception
	{
		throw new Exception("DrawTask must be parameterized");
	}
	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DrawTask(PointInt startPoint, PointInt endPoint, ShapeType shapeType, ShapeStyle shapeStyle)
	{
		//Create shape to draw
		ShapePosition pos = new ShapePosition(startPoint, endPoint);
		Dimensions dim = new Dimensions(pos);
		ShapeCardinality card = ShapeCardinality.calculateCardinality(pos);
		this.shape = (ShapeComponent) ShapeFactory.createShape(
				shapeType,
				dim,
				shapeStyle,
				card,
				pos.getLeft());
	}

	// The opposite of drawing is for the shape to not exist on the canvas.
	@Override
	public void undo()
	{
		ModelAPI.removeShape(shape);
	}

	// To redo, put the shape back on the canvas.
	@Override
	public void redo()
	{
		ModelAPI.addShape(shape);
	}

	@Override
	public void execute()
	{
		ModelAPI.addShape(shape);
		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();
	}
}
