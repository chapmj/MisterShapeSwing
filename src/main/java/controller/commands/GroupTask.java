package controller.commands;

import controller.interfaces.ICanvasControllerCommand;
import model.persistence.CanvasState;
import model.persistence.ModelState;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class GroupTask implements ICanvasControllerCommand {
	private ShapeGroup group;
	private List<ShapeComponent> selection;
	private CanvasState canvasState;
	
	
	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public GroupTask(List<ShapeComponent> selection) {
		this.canvasState = ModelState.getCanvasState();
		this.selection = selection;
	}

	// Wrap selection into a group and update model state.
	// Update fields to persist through undo and redo operations.
	@Override
	public void execute() {
		this.group = new ShapeGroup(selection);
		canvasState.removeComponent(selection);
		canvasState.addShapeGroup(group);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(group);
	}

	// The opposite of adding a group to the model is removing it.
	@Override
	public void undo() {
		canvasState.removeComponent(group);
		canvasState.addComponent(selection);
		canvasState.removeComponent(group);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(selection);
	}

	// Add the group back to the model state.
	@Override
	public void redo() {
		canvasState.removeComponent(selection);
		canvasState.addShapeGroup(group);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(group);
	}
}
