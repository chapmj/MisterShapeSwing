package controller.commands;

import controller.interfaces.ICanvasControllerCommand;
import model.shape.ShapeComponent;
import model.persistence.CanvasState;
import model.persistence.ModelState;

import java.util.ArrayList;
import java.util.List;

/* Responsible for updating the model's canvas state.
 * Used to remove shapes from the canvas.
 */
public class DeleteTask implements ICanvasControllerCommand {

	private CanvasState canvasState;
	private List<ShapeComponent> selection;
	private List<ShapeComponent> selectionCopy;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DeleteTask(List<ShapeComponent> selection) {
		this.canvasState = ModelState.getCanvasState();
		this.selection = selection;
	}

	/* The opposite of deleting a shape from canvas is adding 
	 * it back to the canvas shape list.
	 */
	@Override
	public void undo() {
		canvasState.addComponent(selectionCopy);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void redo() {
		canvasState.removeComponent(selectionCopy);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void execute() {
		selectionCopy = new ArrayList<>(selection);
		canvasState.removeComponent(selectionCopy);
		canvasState.clearComponentSelectionList();
	}
}