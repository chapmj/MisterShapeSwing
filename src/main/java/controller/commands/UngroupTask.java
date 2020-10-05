package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;
import model.interfaces.IShape;
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
public class UngroupTask extends AbstractControllerCommand
{
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

	public UngroupTask(List<ShapeComponent> selection)
	{
	    /* populate shapes into two categories: ungroupedShapes (shapeleafs), groups (shapeGroup composite) */
		Predicate<ShapeComponent> isShapeGroup = component -> component instanceof ShapeGroup;
		Predicate<ShapeComponent> isShape = shapeComponent -> shapeComponent instanceof IShape;

		selection.stream().filter(isShapeGroup)
				  .forEach(groups::add);

		selection.stream().filter(isShape)
				  .forEach(ungroupedShapes::add);

		/* Flatten all shapes in a group into just a shape collection */
		Function<List<IShape>, Stream<ShapeComponent>> toComponentStream = l -> l.stream().map(shape -> (ShapeComponent)shape);
		Function<Optional<ShapeComponent>, Stream<ShapeComponent>> optionToStream = (comp)->Stream.of(comp.get());

		groups.stream()
				.map(Optional::of)
				.filter(Optional::isPresent)
				.flatMap(optionToStream)
				.map(ShapeComponent::getShapes)
				.flatMap(toComponentStream)
				.forEach(groupedShapes::add);
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