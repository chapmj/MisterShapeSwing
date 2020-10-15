package controller.commands;

import controller.api.CopyBufferSvc;
import controller.api.SelectionSvc;
import model.interfaces.IShape;
import model.shape.ShapeComponent;

import java.util.ArrayList;
import java.util.List;

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
		var shapeCopies = new ArrayList<>(shapes);

		for (IShape shape : shapes)
		{
			var component = (ShapeComponent) shape;
			var componentCopy = component.clone();
			shapeCopies.add(componentCopy);
		}
		return shapeCopies;
	}

}

