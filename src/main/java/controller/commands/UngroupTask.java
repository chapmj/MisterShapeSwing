package controller.commands;

import controller.api.AddShapesSvc;
import controller.api.AddToSelectionSvc;
import controller.api.ClearSelectionSvc;
import controller.api.RemoveShapeSvc;
import model.CommandHistory;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.List;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class UngroupTask extends AbstractControllerTask
{
	private final List<ShapeComponent> groups;
	private final List<ShapeComponent> groupedShapes;
	private final List<ShapeComponent> ungroupedShapes;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unused")
	private UngroupTask() throws Exception
	{
	    throw new Exception("UngroupTask must be parameterized");
	}

	public UngroupTask(List<ShapeComponent> groups, List<ShapeComponent>groupedShapes, List<ShapeComponent>ungroupedShapes)
	{
	    this.groups = groups;
	    this.groupedShapes = groupedShapes;
	    this.ungroupedShapes = ungroupedShapes;
	}


	//Split group, remove group from canvas, add child objects to canvas.
	@Override
	public void execute()
	{
		ungroup();
		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();
	}

	private void ungroup()
	{
		//TODO: emit model commands for better unit test
		RemoveShapeSvc.accept(groups);
		AddShapesSvc.accept(groupedShapes);
		AddShapesSvc.accept(ungroupedShapes);
		ClearSelectionSvc.apply();
		AddToSelectionSvc.accept(ungroupedShapes);
		AddToSelectionSvc.accept(groupedShapes);
	}

	// Remove shapes from canvas and add back group.
	@Override
	public void undo()
	{
		RemoveShapeSvc.accept(groupedShapes);
		AddShapesSvc.accept(groups);
		ClearSelectionSvc.apply();
		AddShapesSvc.accept(ungroupedShapes);
		AddShapesSvc.accept(groups);
	}

	// Remove group and add back shapes.
	@Override
	public void redo()
	{
	    ungroup();
	}
}