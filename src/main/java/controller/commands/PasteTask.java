package controller.commands;

import controller.api.AddShapesSvc;
import controller.api.AddToSelectionSvc;
import controller.api.RemoveShapeSvc;
import controller.api.ShapeLocationSvc;
import model.CommandHistory;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class PasteTask extends AbstractControllerTask
{
	private static final Integer INC_X = 20;
	private static final Integer INC_Y = 20;

	private final PointInt pasteLocation;
	private final List<ShapeComponent> shapes;

	@SuppressWarnings("unused")
	private PasteTask() throws Exception
	{
		throw new Exception("PasteTask must be parameterized");
	}

	public PasteTask(PointInt pasteLocation, List<ShapeComponent> shapes)
	{
	    this.pasteLocation = pasteLocation;
	    this.shapes = shapes;
	}

	/* Get copy buffer and add shapes to model.  Positions remain relative to their
	 * original position, but set at a paste position initialized to the corner of
	 * the canvas.
	 */
	@Override
	public void execute()
	{
		shapes.stream().forEach((shapeComponent) -> {
			var moveGroup = new ShapeGroup(shapes);
			var pasteDelta = new PointInt(
					(pasteLocation.getX() + INC_X) - moveGroup.getAnchor().getX(),
					(pasteLocation.getY() + INC_Y) - moveGroup.getAnchor().getY());

			var x = shapeComponent.getAnchor().getX() + pasteDelta.getX();
			var y = shapeComponent.getAnchor().getY() + pasteDelta.getY();

			ShapeLocationSvc.accept(shapeComponent, new PointInt(x, y));

		});

		incrementPasteLocation();
		AddShapesSvc.accept(shapes);
		ModelAPI.commit();//changes are made, update observers to redraw

		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();

	}

	/* The opposite of Pasting a objects is removing them from the canvas.
	 * Objects will not be garbage collected until this task object is 
	 * dereferenced.
	 */
	@Override
	public void undo()
	{
		decrementPasteLocation();
		RemoveShapeSvc.accept(shapes);
	}

	// Put the objects back in the shape list.
	@Override
	public void redo()
	{
		incrementPasteLocation();
		AddShapesSvc.accept(shapes);
	}

	// Track where to paste something on the canvas.
	public void decrementPasteLocation()
	{
		Integer deltaX = INC_X * shapes.size();
		Integer deltaY = INC_Y * shapes.size();
		pasteLocation.subtract(new PointInt(deltaX, deltaY));
	}

	public void incrementPasteLocation()
	{
		Integer deltaX = INC_X * shapes.size();
		Integer deltaY = INC_Y * shapes.size();
		pasteLocation.add(new PointInt(deltaX, deltaY));
	}
	
}