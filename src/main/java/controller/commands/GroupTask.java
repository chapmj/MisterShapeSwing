package controller.commands;

import controller.api.AddShapesSvc;
import controller.api.AddToSelectionSvc;
import controller.api.ClearSelectionSvc;
import controller.api.RemoveShapeSvc;
import model.CommandHistory;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapeGroup;

import java.util.List;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class GroupTask extends AbstractControllerTask
{
	private final List<IShape> shapes;
	private final ShapeGroup group;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unused")
    private GroupTask() throws Exception
	{
		throw new Exception("GroupTask must be parameterized");
	}

	public GroupTask(List<IShape> shapes, ShapeGroup shapeGroup)
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
	    //Perform Grouping
		RemoveShapeSvc.accept(shapes);
		AddShapesSvc.accept(group);

		//Update selection
		ClearSelectionSvc.apply();
		AddToSelectionSvc.accept(group);

	}

	@Override
	public void undo()
	{
		//Perform Ungrouping
		RemoveShapeSvc.accept(group);
		AddShapesSvc.accept(shapes);

		//Update selection
        ClearSelectionSvc.apply();
		AddToSelectionSvc.accept(shapes);
	}

	@Override
	public void redo()
	{
		group();
	}
}
