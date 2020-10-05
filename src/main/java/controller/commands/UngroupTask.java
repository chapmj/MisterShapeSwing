package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class UngroupTask extends AbstractControllerCommand
{
	private List<ShapeComponent> selection;
	private List<ShapeComponent> groups;
	private List<ShapeComponent> groupedShapes;
	private List<ShapeComponent> ungroupedShapes;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unchecked")
	public UngroupTask()
	{
		var s = (ArrayList<ShapeComponent>) ModelAPI.getSelection();
		var cp = s.clone();
		this.selection = (List<ShapeComponent>) cp;
		groups = new ArrayList<>();
		groupedShapes = new ArrayList<>();
		ungroupedShapes = new ArrayList<>();
	}
	/*
	public UngroupTask(List<ShapeComponent> selection)
	{
	}*/

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
		initializeGroupedShapes();
		ModelAPI.removeShapes(groups);
		ModelAPI.addShapes(groupedShapes);
		ModelAPI.clearSelection();
		ModelAPI.addComponentSelection(ungroupedShapes);
		ModelAPI.addComponentSelection(groupedShapes);
	}

	// Set up instance fields.
	private void initializeGroupedShapes()
	{
		groups.clear();
		selection.stream()
			.filter ((component) -> component instanceof ShapeGroup)
			.forEach(groups::add);

		ungroupedShapes.clear();
		selection.stream()
			.filter ((component) -> component instanceof IShape)
			.forEach(ungroupedShapes::add);

		groupedShapes.clear();
		groups.stream()
			.flatMap(component -> Stream.of(splitGroup(component)))
			.forEach(groupedShapes::addAll);
	}
	
	// Flatten group to a list of shapes.
	private List<ShapeComponent> splitGroup(ShapeComponent component)
	{
		if (component instanceof ShapeGroup)
		{
			return component.getShapes().stream()
					.map (shape -> (ShapeComponent) shape)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
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