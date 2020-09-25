package controller.commands;

import java.util.ArrayList;

import controller.CanvasController;
import controller.CanvasUtils;
import controller.interfaces.ICanvasControllerCommand;
import model.PointInt;
import model.ShapeGroup;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

/* Responsible for updating the model's canvas state.
 * Used to add shapes to the canvas based on shape components
 * stored in the copy buffer.
 */
public class PasteTask implements ICanvasControllerCommand {

	private ArrayList<ShapeComponent> copyBuffer;
	private ArrayList<ShapeComponent> shapeCopies;
	private Integer incX = 20;
	private Integer incY = 20;
	private CanvasState canvasState;
	private CanvasController canvasController = CanvasController.getInstance();

	public PasteTask() {
		this.canvasState = canvasController.getCanvasState();
	}

	/* Get copy buffer and add shapes to model.  Positions remain relative to their
	 * original position, but set at a paste position initialized to the corner of
	 * the canvas.
	 */
	@Override
	public void execute() throws Exception {
		copyBuffer = canvasState.getComponentCopyBuffer();
		shapeCopies = new ArrayList<>();
		PointInt pasteLocation = canvasState.getLastPasteLocation();
		
		for (ShapeComponent comp : copyBuffer)
			shapeCopies.add(comp.clone());

		ShapeGroup moveGroup = new ShapeGroup(shapeCopies);
		PointInt pasteDelta = new PointInt(
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
	public void undo() throws Exception {
		decrementPasteLocation();
		canvasState.removeComponent(shapeCopies);
	}

	// Put the objects back in the shape list.
	@Override
	public void redo() throws Exception {
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