package controller.commands;

import model.CommandHistory;
import model.PointInt;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class PasteTask extends AbstractControllerCommand
{

	private final PointInt pasteLocation;
	private final List<ShapeComponent> shapeCopies;
	private final Integer incX = 20;
	private final Integer incY = 20;
	private final BiConsumer<ShapeComponent, PointInt> moveShapeInModel = ModelAPI::setShapeLocation;

	@SuppressWarnings("unused")
	private PasteTask() throws Exception
	{
		throw new Exception("PasteTask must be parameterized");
	}

	public PasteTask(PointInt pasteLocation, List<ShapeComponent> copyBuffer)
	{
	    this.pasteLocation = pasteLocation;
		this.shapeCopies = copyBuffer.stream()
				.map(ShapeComponent::clone)
				.collect(Collectors.toList());
	}

	/* Get copy buffer and add shapes to model.  Positions remain relative to their
	 * original position, but set at a paste position initialized to the corner of
	 * the canvas.
	 */
	@Override
	public void execute()
	{
		shapeCopies.stream().forEach((shapeComponent) -> {
			var moveGroup = new ShapeGroup(shapeCopies);
			var pasteDelta = new PointInt(
					(pasteLocation.getX() + incX) - moveGroup.getAnchor().getX(),
					(pasteLocation.getY() + incY) - moveGroup.getAnchor().getY());

			var x = shapeComponent.getAnchor().getX() + pasteDelta.getX();
			var y = shapeComponent.getAnchor().getY() + pasteDelta.getY();

			ModelAPI.setShapeLocation(shapeComponent, new PointInt(x, y));

		});

		incrementPasteLocation();
		ModelAPI.addShapes(shapeCopies);
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
		ModelAPI.removeShapes(shapeCopies);
	}

	// Put the objects back in the shape list.
	@Override
	public void redo()
	{
		incrementPasteLocation();
		ModelAPI.addShapes(shapeCopies);
	}

	// Track where to paste something on the canvas.
	public void decrementPasteLocation()
	{
		Integer deltaX = incX * shapeCopies.size();
		Integer deltaY = incY * shapeCopies.size();
		pasteLocation.subtract(new PointInt(deltaX, deltaY));
	}

	public void incrementPasteLocation()
	{
		Integer deltaX = incX * shapeCopies.size();
		Integer deltaY = incY * shapeCopies.size();
		pasteLocation.add(new PointInt(deltaX, deltaY));
	}
	
}