package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class GroupTask extends AbstractControllerCommand
{
	private final List<ShapeComponent> shapes;
	private final ShapeGroup group;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unused")
    private GroupTask() throws Exception
	{
		throw new Exception("GroupTask must be parameterized");
	}

	public GroupTask(List<ShapeComponent> selection)
	{
		this.shapes = selection;
		this.group = new ShapeGroup(shapes);
	}

	// Wrap selection into a group and update model state.
	// Update fields to persist through undo and redo operations.
	@Override
	public void execute()
	{
        this.group();
		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();
	}

	private void group()
	{
		ModelAPI.removeShapes(shapes);
		ModelAPI.addShapeGroup(group);
		ModelAPI.clearSelection();
		ModelAPI.addComponentToSelection(group);

	}

	// The opposite of adding a group to the model is removing it.
	@Override
	public void undo()
	{
		ModelAPI.removeShape(group);
		ModelAPI.addShapes(shapes);
        ModelAPI.clearSelection();
		ModelAPI.addComponentToSelection(shapes);
	}

	// Add the group back to the model state.
	@Override
	public void redo()
	{
		ModelAPI.removeShapes(shapes);
		ModelAPI.addShapeGroup(group);
        ModelAPI.clearSelection();
		ModelAPI.addComponentToSelection(group);
	}
}
