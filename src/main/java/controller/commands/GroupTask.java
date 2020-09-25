package controller.commands;

import java.util.ArrayList;
import controller.CanvasController;
import controller.interfaces.ICanvasControllerCommand;
import model.ShapeGroup;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class GroupTask implements ICanvasControllerCommand {
	private ShapeGroup group;
	private CanvasController canvasController;
	private ArrayList<ShapeComponent> selection;
	private CanvasState canvasState;
	
	
	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public GroupTask(ArrayList<ShapeComponent> selection) {
		this.canvasController = CanvasController.getInstance();
		this.canvasState = canvasController.getCanvasState();
		this.selection = selection;
	}

	// Wrap selection into a group and update model state.
	// Update fields to persist through undo and redo operations.
	@Override
	public void execute() throws Exception {
		this.group = new ShapeGroup(selection);
		canvasController.removeComponents(selection);
		canvasController.addShapeGroup(group);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(group);
	}

	// The opposite of adding a group to the model is removing it.
	@Override
	public void undo() throws Exception {
		canvasController.removeComponent(group);
		canvasController.addComponents(selection);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(selection);
	}

	// Add the group back to the model state.
	@Override
	public void redo() throws Exception {
		canvasController.removeComponents(selection);
		canvasController.addShapeGroup(group);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(group);
	}
}
