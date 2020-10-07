package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.Shape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
		ModelAPI.removeShapes(groups);
		ModelAPI.addShapes(groupedShapes);
		ModelAPI.clearSelection();
		ModelAPI.addComponentToSelection(ungroupedShapes);
		ModelAPI.addComponentToSelection(groupedShapes);
	}

	// Remove shapes from canvas and add back group.
	@Override
	public void undo()
	{
		ModelAPI.removeShapes(groupedShapes);
		ModelAPI.addShapes(groups);
		ModelAPI.clearSelection();
		ModelAPI.addShapes(ungroupedShapes);
		ModelAPI.addShapes(groups);
	}

	// Remove group and add back shapes.
	@Override
	public void redo()
	{
	    ungroup();
	}
}