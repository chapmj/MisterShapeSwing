package controller.commands;

import java.util.ArrayList;

import controller.CanvasController;
import controller.interfaces.ICanvasControllerCommand;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

/* Responsible for updating the model's canvas state.
 * Used to remove shapes from the canvas.
 */
public class DeleteTask implements ICanvasControllerCommand {

	private CanvasState canvasState;
	private ArrayList<ShapeComponent> selection;
	private ArrayList<ShapeComponent> selectionCopy;
	private CanvasController canvasController = CanvasController.getInstance();

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DeleteTask(ArrayList<ShapeComponent> selection) {
		this.canvasState = canvasController.getCanvasState();
		this.selection = selection;
	}

	/* The opposite of deleting a shape from canvas is adding 
	 * it back to the canvas shape list.
	 */
	@Override
	public void undo() throws Exception {
		canvasState.addComponent(selectionCopy);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void redo() throws Exception {
		canvasState.removeComponent(selectionCopy);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void execute() throws Exception {
		selectionCopy = new ArrayList<ShapeComponent>(selection);
		canvasState.removeComponent(selectionCopy);
		canvasState.clearComponentSelectionList();
	}
}