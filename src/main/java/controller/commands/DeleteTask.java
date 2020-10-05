package controller.commands;

import model.CommandHistory;
import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;

/* Responsible for updating the model's canvas state.
 * Used to remove shapes from the canvas.
 */
public class DeleteTask extends AbstractControllerCommand
{
	private final List<ShapeComponent> shapes = new ArrayList<>();

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unused")
	private DeleteTask() throws Exception
	{
		throw new Exception("GroupTask must be parameterized");
	}

	public DeleteTask(List<ShapeComponent> selection)
	{
		this.shapes.addAll(selection);
	}

	// Remove the shape from the model's shape list
	@Override
	public void execute()
	{
		ModelAPI.removeShapes(shapes);
		ModelAPI.clearSelection();
		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();
	}

	/* The opposite of deleting a shape from canvas is adding
	 * it back to the canvas shape list.
	 */
	@Override
	public void undo()
	{
		ModelAPI.addShapes(shapes);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void redo()
	{
		ModelAPI.removeShapes(shapes);
	}

}