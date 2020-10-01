package controller.commands;

import controller.interfaces.ICanvasControllerCommand;
import model.shape.ShapeComponent;
import model.persistence.CanvasState;
import model.persistence.ModelState;

import java.util.ArrayList;
import java.util.List;

/* Responsible for updating the model's copy buffer with
 * the current selection on the canvas.
 */

public class CopyTask implements ICanvasControllerCommand {
	private List<ShapeComponent> selection;
	private CanvasState canvasState;


	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public CopyTask() {
		selection = ModelState.getCanvasState().getComponentSelectionList();
		canvasState = ModelState.getCanvasState();
	}

	// Undo/redo not implemented because it would be confusing UX.
	@Override
	public void undo() { }

	@Override
	public void redo() { }

	@Override
	public void execute() {
		var copyBuffer = new ArrayList<>(selection);
		canvasState.setComponentCopyBuffer(copyBuffer);
	}

}

