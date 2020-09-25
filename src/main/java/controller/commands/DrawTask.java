package controller.commands;

import controller.CanvasController;
import controller.JPaintController;
import controller.interfaces.ICanvasControllerCommand;
import model.Dimensions;
import model.PointInt;
import model.ShapeCardinality;
import model.ShapeFactory;
import model.ShapePosition;
import model.ShapeStyle;
import model.ShapeType;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas.
 */
public class DrawTask implements ICanvasControllerCommand {

	private PointInt startPoint;
	private PointInt endPoint;
	private ShapeType shapeType;
	private ShapeStyle shapeStyle;
	private IShape shape;
	JPaintController controller = JPaintController.getInstance();
	CanvasController canvasController = CanvasController.getInstance();

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DrawTask(PointInt startPoint, PointInt endPoint) {
		this.shapeType = controller.getActiveShape();
		this.shapeStyle = controller.getShapeStyleFromApp().clone();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	// The opposite of drawing is for the shape to not exist on the canvas.
	@Override
	public void undo() throws Exception {
		canvasController.removeComponent((ShapeComponent)shape);
	}

	// To redo, put the shape back on the canvas.
	@Override
	public void redo() throws Exception {
		addShapeToShapeList();
	}

	@Override
	public void execute() throws Exception {
		createShape();
		addShapeToShapeList();
	}
	
	// Helper functions
	private void addShapeToShapeList() throws Exception {
		canvasController.addComponent((ShapeComponent)shape);
	}
	
//	private void removeShapeFromShapeList() throws Exception {
//		canvasController.removeComponent((ShapeComponent) shape);
//	}
	
	// Makes shape objects to store as data items.
	private void createShape() {
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
