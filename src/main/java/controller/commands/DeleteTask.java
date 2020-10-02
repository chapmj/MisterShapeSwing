package controller.commands;

import model.api.ModelAPI;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;

/* Responsible for updating the model's canvas state.
 * Used to remove shapes from the canvas.
 */
public class DeleteTask extends AbstractControllerCommand
{
	private List<ShapeComponent> selection;
	private List<ShapeComponent> selectionCopy;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public DeleteTask(List<ShapeComponent> selection)
	{
		this.selection = selection;
	}

	// Remove the shape from the model's shape list
	@Override
	public void execute()
	{
		selectionCopy = new ArrayList<>(selection);
		ModelAPI.removeShapes(selectionCopy);
		ModelAPI.clearSelection();
	}

	/* The opposite of deleting a shape from canvas is adding
	 * it back to the canvas shape list.
	 */
	@Override
	public void undo()
	{
		ModelAPI.addShapes(selectionCopy);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void redo()
	{
		ModelAPI.removeShapes(selectionCopy);
	}

}