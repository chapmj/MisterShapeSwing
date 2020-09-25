package controller.commands;

import java.util.ArrayList;

import controller.CanvasController;
import controller.interfaces.ICanvasControllerCommand;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

/* Responsible for updating the model's copy buffer with
 * the current selection on the canvas.
 */

public class CopyTask implements ICanvasControllerCommand {
	private ArrayList<ShapeComponent> selection;
	private CanvasState canvasState;
	private CanvasController canvasController = CanvasController.getInstance();


	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public CopyTask() {
		selection = canvasController.getCanvasState().getComponentSelectionList();
		canvasState = canvasController.getCanvasState();
	}

	// Undo/redo not implemented because it would be confusing UX.
	@Override
	public void undo() throws Exception { }

	@Override
	public void redo() throws Exception { }

	@Override
	public void execute() throws Exception {
		ArrayList<ShapeComponent> copyBuffer = new ArrayList<>();
		copyBuffer.addAll(selection);
		canvasState.setComponentCopyBuffer(copyBuffer);
	}

}

