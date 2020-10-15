package controller.commands;

import controller.api.AddShapesSvc;
import controller.api.ClearSelectionSvc;
import controller.api.RemoveShapeSvc;
import model.CommandHistory;
import model.api.ModelAPI;
import model.interfaces.IShape;

import java.util.List;

/* Responsible for updating the model's canvas state.
 * Used to remove shapes from the canvas.
 */
public class DeleteTask extends AbstractControllerTask
{
	private final List<IShape> shapes;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	@SuppressWarnings("unused")
	private DeleteTask() throws Exception
	{
		throw new Exception("DeleteTask must be parameterized");
	}

	public DeleteTask(List<IShape> selection)
	{
		this.shapes = selection;
	}

	// Remove the shape from the model's shape list
	@Override
	public void execute()
	{
		RemoveShapeSvc.accept(shapes);
		ClearSelectionSvc.apply();
		CommandHistory.add(this);
		ModelAPI.notifyCanvasObservers();
	}

	/* The opposite of deleting a shape from canvas is adding
	 * it back to the canvas shape list.
	 */
	@Override
	public void undo()
	{
		AddShapesSvc.accept(shapes);
	}

	// Remove the shape from the model's shape list 
	@Override
	public void redo()
	{
		RemoveShapeSvc.accept(shapes);
	}

}