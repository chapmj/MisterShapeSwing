package controller.commands;

import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class GroupTask extends AbstractControllerCommand
{
	private ShapeGroup group;
	private List<ShapeComponent> selection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public GroupTask(List<ShapeComponent> selection)
	{
		this.selection = selection;
	}

	// Wrap selection into a group and update model state.
	// Update fields to persist through undo and redo operations.
	@Override
	public void execute()
	{
		this.group = new ShapeGroup(selection);
		ModelAPI.removeShapes(selection);
		ModelAPI.addShapeGroup(group);
		ModelAPI.clearSelection();
		ModelAPI.addComponentSelection(group);
	}

	// The opposite of adding a group to the model is removing it.
	@Override
	public void undo()
	{
		ModelAPI.removeShape(group);
		ModelAPI.addShapes(selection);
        ModelAPI.removeShape(group);
        ModelAPI.clearSelection();
		ModelAPI.addComponentSelection(selection);
	}

	// Add the group back to the model state.
	@Override
	public void redo()
	{
		ModelAPI.removeShapes(selection);
		ModelAPI.addShapeGroup(group);
        ModelAPI.clearSelection();
		ModelAPI.addComponentSelection(group);
	}
}
