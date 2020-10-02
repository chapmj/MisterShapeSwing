package controller.commands;

import controller.JPaintController;
import controller.interfaces.ICanvasControllerCommand;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.persistence.CanvasState;
import model.persistence.ModelState;
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
	private CanvasState canvasState;
	private List<ShapeComponent> groupedShapes;
	private List<ShapeComponent> ungroupedShapes;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public UngroupTask(List<ShapeComponent> selection)
	{
		this.selection = new ArrayList<>(selection);
		this.canvasState = ModelState.getCanvasState();
		groups = new ArrayList<>();
		groupedShapes = new ArrayList<>();
		ungroupedShapes = new ArrayList<>();
	}

	//Split group, remove group from canvas, add child objects to canvas.
	@Override
	public void execute()
	{
		initializeGroupedShapes();
		canvasState.removeComponent(groups);
		canvasState.addComponent(groupedShapes);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(ungroupedShapes);
		canvasState.addComponentSelection(groupedShapes);

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
		canvasState.removeComponent(groupedShapes);
		canvasState.addComponent(groups);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(ungroupedShapes);
		canvasState.addComponentSelection(groups);
	}

	// Remove group and add back shapes.
	@Override
	public void redo()
	{
		execute();
	}
}