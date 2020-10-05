package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class UngroupTask extends AbstractControllerCommand
{
	private final List<ShapeComponent> selection = new ArrayList<>();
	private final List<ShapeComponent> groups = new ArrayList<>();
	private final List<ShapeComponent> groupedShapes = new ArrayList<>();
	private final List<ShapeComponent> ungroupedShapes = new ArrayList<>();

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unused")
	private UngroupTask() throws Exception
	{
	    throw new Exception("UngroupTask must be parameterized");
	}

	public UngroupTask(List<ShapeComponent> s)
	{
		//Constructor which builds task based on argument.  Future unit test
		//I'd prefer to use this for creating all tasks.  See event registry
		this.selection.addAll(s);
	}


	//Split group, remove group from canvas, add child objects to canvas.
	@Override
	public void execute()
	{
		groups.clear();
		ungroupedShapes.clear();
		groupedShapes.clear();

		selection.stream()
				.filter ((component) -> component instanceof ShapeGroup)
				.forEach(groups::add);

		selection.stream()
				.filter ((component) -> component instanceof IShape)
				.forEach(ungroupedShapes::add);

		groups.stream()
				.map(Optional::of)
				.filter(Optional::isPresent)
				.flatMap((c)->Stream.of(c.get()))
				.map(ShapeComponent::getShapes)
				.flatMap((l) ->
						l.stream()
								.map((s) -> (ShapeComponent) s))
				.forEach(groupedShapes::add);

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