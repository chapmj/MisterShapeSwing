package controller.commands;

import controller.CanvasUtils;
import controller.interfaces.ICanvasControllerCommand;
import model.PointInt;
import model.persistence.ModelState;
import model.shape.ShapeGroup;
import model.shape.ShapeComponent;
import model.persistence.CanvasState;

import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class PasteTask implements ICanvasControllerCommand {

	private List<ShapeComponent> shapeCopies;
	private Integer incX = 20;
	private Integer incY = 20;
	private CanvasState canvasState;

	public PasteTask() {
		this.canvasState = ModelState.getCanvasState();
	}

	/* Get copy buffer and add shapes to model.  Positions remain relative to their
	 * original position, but set at a paste position initialized to the corner of
	 * the canvas.
	 */
	@Override
	public void execute() {
		PointInt pasteLocation = canvasState.getLastPasteLocation();

		var copyBuffer = canvasState.getComponentCopyBuffer();
		this.shapeCopies = copyBuffer.stream()
				.map(ShapeComponent::clone)
				.collect(Collectors.toList());

		var moveGroup = new ShapeGroup(shapeCopies);
		var pasteDelta = new PointInt(
				(pasteLocation.getX() + incX) - moveGroup.getAnchor().getX(),
				(pasteLocation.getY() + incY) - moveGroup.getAnchor().getY());

		CanvasUtils.moveShapes(shapeCopies, pasteDelta);	
		incrementPasteLocation();
		canvasState.addComponent(shapeCopies);
	}

	/* The opposite of Pasting a objects is removing them from the canvas.
	 * Objects will not be garbage collected until this task object is 
	 * dereferenced.
	 */
	@Override
	public void undo() {
		decrementPasteLocation();
		canvasState.removeComponent(shapeCopies);
	}

	// Put the objects back in the shape list.
	@Override
	public void redo() {
		incrementPasteLocation();
		canvasState.addComponent(shapeCopies);
	}

	// Track where to paste something on the canvas.
	public void decrementPasteLocation() {
		Integer deltaX = incX * shapeCopies.size();
		Integer deltaY = incY * shapeCopies.size();
		canvasState.getLastPasteLocation().subtract(new PointInt(deltaX,deltaY));
	}

	public void incrementPasteLocation() {
		Integer deltaX = incX * shapeCopies.size();
		Integer deltaY = incY * shapeCopies.size();
		canvasState.getLastPasteLocation().add(new PointInt(deltaX,deltaY));
	}
	
}