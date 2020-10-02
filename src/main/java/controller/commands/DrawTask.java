package controller.commands;

import controller.interfaces.ICanvasControllerCommand;
import model.Dimensions;
import model.PointInt;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.CanvasState;
import model.persistence.ModelState;
import model.shape.*;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas.
 */
public class DrawTask extends AbstractControllerCommand
{

	private final CanvasState canvasState;
	private PointInt startPoint;
	private PointInt endPoint;
	private ShapeType shapeType;
	private ShapeStyle shapeStyle;
	private IShape shape;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DrawTask(PointInt startPoint, PointInt endPoint, IApplicationState appState)
	{
		this.shapeType = appState.getShapeType();
		this.shapeStyle = appState.getShapeStyle().clone();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.canvasState = ModelState.getCanvasState();
	}

	// The opposite of drawing is for the shape to not exist on the canvas.
	@Override
	public void undo()
	{
		canvasState.removeComponent((ShapeComponent)shape);
	}

	// To redo, put the shape back on the canvas.
	@Override
	public void redo()
	{
		addShapeToShapeList();
	}

	@Override
	public void execute()
	{
		createShape();
		addShapeToShapeList();
	}
	
	// Helper functions
	private void addShapeToShapeList()
	{
		canvasState.addComponent((ShapeComponent)shape);
	}
	
	// Makes shape objects to store as data items.
	private void createShape()
	{
		ShapePosition pos = new ShapePosition(startPoint, endPoint);
		Dimensions dim = new Dimensions(pos);
		ShapeCardinality card = ShapeCardinality.calculateCardinality(pos);
        shape = ShapeFactory.createShape(
			shapeType,
			dim,	
			shapeStyle,
			card,
			pos.getLeft());
	}
}
