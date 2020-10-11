package controller.commands;

import controller.api.AddShapesSvc;
import controller.api.AddToSelectionSvc;
import controller.api.ClearSelectionSvc;
import controller.api.RemoveShapeSvc;
import model.CommandHistory;
import model.api.ModelAPI;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class GroupTask extends AbstractControllerTask
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

	public GroupTask(List<ShapeComponent> shapes, ShapeGroup shapeGroup)
	{
	    this.shapes = shapes;
	    this.group = shapeGroup;
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
		RemoveShapeSvc.accept(shapes);
		AddShapesSvc.accept(group);
		ClearSelectionSvc.apply();

		AddToSelectionSvc.accept(group);

	}

	// The opposite of adding a group to the model is removing it.
	@Override
	public void undo()
	{
		RemoveShapeSvc.accept(group);
		AddShapesSvc.accept(shapes);
        ClearSelectionSvc.apply();
		AddToSelectionSvc.accept(shapes);
	}

	// Add the group back to the model state.
	@Override
	public void redo()
	{
		RemoveShapeSvc.accept(shapes);
		AddShapesSvc.accept(group);
        ClearSelectionSvc.apply();
		AddToSelectionSvc.accept(group);
	}
}
