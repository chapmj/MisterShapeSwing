package controller.commands;

import controller.api.AddShapesSvc;
import controller.api.PasteLocationSvc;
import controller.api.RemoveShapeSvc;
import controller.api.ShapeLocationSvc;
import model.CommandHistory;
import model.PointInt;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapeFactory;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class PasteTask extends AbstractControllerTask
{
	private static final Integer INC_X = 20;
	private static final Integer INC_Y = 20;

	private PointInt pasteLocation;
	private final List<IShape> shapes;
	private List<IShape> dupes;

	@SuppressWarnings("unused")
	private PasteTask() throws Exception
	{
		throw new Exception("PasteTask must be parameterized");
	}

	public PasteTask(PointInt pasteLocation, List<IShape> shapes)
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
		var pasteDelta = createPasteAnchor();

		dupes = shapes.stream()
				.map(ShapeFactory::createShape)
				.collect(Collectors.toList());
		//update shape coordinates
		for (IShape dupe : dupes)
		{
			var x = dupe.getAnchor().getX() + pasteDelta.getX();
			var y = dupe.getAnchor().getY() + pasteDelta.getY();
			ShapeLocationSvc.accept(dupe, new PointInt(x, y));
		}

		AddShapesSvc.accept(dupes);

		CommandHistory.add(this);
		PasteLocationSvc.accept(pasteLocation);
		ModelAPI.notifyCanvasObservers();
	}

	private PointInt createPasteAnchor()
	{
		var moveGroup = new ShapeGroup(shapes);
		var deltaX = moveGroup.getAnchor().getX();
		var deltaY = moveGroup.getAnchor().getY();

		var pasteAnchorX = pasteLocation.getX() + INC_X;
		var pasteAnchorY = pasteLocation.getY() + INC_X;

		incrementPasteLocation();
		return new PointInt(pasteAnchorX - deltaX, pasteAnchorY - deltaY);
	}

	/* The opposite of Pasting a objects is removing them from the canvas.
	 * Objects will not be garbage collected until this task object is 
	 * dereferenced.
	 */
	@Override
	public void undo()
	{
		decrementPasteLocation();
		PasteLocationSvc.accept(pasteLocation);
		RemoveShapeSvc.accept(dupes);
	}

	// Put the objects back in the shape list.
	@Override
	public void redo()
	{
		incrementPasteLocation();
		PasteLocationSvc.accept(pasteLocation);
		AddShapesSvc.accept(dupes);
	}

	// Track where to paste something on the canvas.
	public void decrementPasteLocation()
	{
		Integer deltaX = INC_X * shapes.size();
		Integer deltaY = INC_Y * shapes.size();
		pasteLocation = pasteLocation.subtract(deltaX, deltaY);
	}

	public void incrementPasteLocation()
	{
		Integer deltaX = INC_X * shapes.size();
		Integer deltaY = INC_Y * shapes.size();
		pasteLocation = pasteLocation.add(deltaX, deltaY);
	}
	
}