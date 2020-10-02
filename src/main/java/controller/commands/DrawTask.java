package controller.commands;

import model.Dimensions;
import model.PointInt;
import model.api.ModelAPI;
import model.interfaces.IApplicationState;
import model.shape.*;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas.
 */
public class DrawTask extends AbstractControllerCommand
{
	private PointInt startPoint;
	private PointInt endPoint;
	private ShapeType shapeType;
	private ShapeStyle shapeStyle;
	private ShapeComponent shape;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DrawTask(PointInt startPoint, PointInt endPoint, IApplicationState appState)
	{
		this.shapeType = appState.getShapeType();
		this.shapeStyle = appState.getShapeStyle().clone();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
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
	    //Create shape to draw
		ShapePosition pos = new ShapePosition(startPoint, endPoint);
		Dimensions dim = new Dimensions(pos);
		ShapeCardinality card = ShapeCardinality.calculateCardinality(pos);
		shape = (ShapeComponent) ShapeFactory.createShape(
				shapeType,
				dim,
				shapeStyle,
				card,
				pos.getLeft());
		ModelAPI.addShape(shape);
	}
}
