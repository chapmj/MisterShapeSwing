package controller.commands;

import controller.api.CopyBufferSvc;
import controller.api.SelectionSvc;
import model.interfaces.IShape;
import model.shape.ShapeFactory;

import java.util.List;
import java.util.stream.Collectors;

/* Responsible for updating the model's copy buffer with
 * the current selection on the canvas.
 */

public class CopyTask extends AbstractControllerTask
{
	@Override
	public void execute()
	{
		var selection = SelectionSvc.get();
		var shapeCopies = copy(selection);
		CopyBufferSvc.put(shapeCopies);
		
	}
	
	public List<IShape> copy(List<IShape> shapes)
	{
		var shapeCopies = shapes.stream()
				.map(ShapeFactory::createShape)
				.collect(Collectors.toList());

		return shapeCopies;
	}
}

