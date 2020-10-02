package controller.commands;

import controller.CanvasUtils;
import model.PointInt;
import model.api.ModelAPI;
import model.persistence.CanvasState;
import model.persistence.ModelState;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class PasteTask extends AbstractControllerCommand
{

	private List<ShapeComponent> shapeCopies;
	private Integer incX = 20;
	private Integer incY = 20;

	public PasteTask()
	{
	}

	/* Get copy buffer and add shapes to model.  Positions remain relative to their
	 * original position, but set at a paste position initialized to the corner of
	 * the canvas.
	 */
	@Override
	public void execute()
	{
		PointInt pasteLocation = ModelAPI.getPasteLocation();

		var copyBuffer = ModelAPI.getComponentBuffer();

		this.shapeCopies = copyBuffer.stream()
				.map(ShapeComponent::clone)
				.collect(Collectors.toList());

		shapeCopies.stream().forEach((shapeComponent) -> {
			var moveGroup = new ShapeGroup(shapeCopies);
			var pasteDelta = new PointInt(
					(pasteLocation.getX() + incX) - moveGroup.getAnchor().getX(),
					(pasteLocation.getY() + incY) - moveGroup.getAnchor().getY());
			var x = shapeComponent.getAnchor().getX() + pasteDelta.getX();
			var y = shapeComponent.getAnchor().getY() + pasteDelta.getY();
			ModelAPI.setShapeLocation(shapeComponent, x ,y);
		});

		incrementPasteLocation();
		ModelAPI.addShapes(shapeCopies);
		ModelAPI.commit();//changes are made, update observers to redraw

	}

	/* The opposite of Pasting a objects is removing them from the canvas.
	 * Objects will not be garbage collected until this task object is 
	 * dereferenced.
	 */
	@Override
	public void undo()
	{
		decrementPasteLocation();
		ModelAPI.removeComponent(shapeCopies);
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
		ModelAPI.getPasteLocation().subtract(new PointInt(deltaX,deltaY));
	}

	public void incrementPasteLocation()
	{
		Integer deltaX = incX * shapeCopies.size();
		Integer deltaY = incY * shapeCopies.size();
		ModelAPI.getPasteLocation().add(new PointInt(deltaX,deltaY));
	}
	
}