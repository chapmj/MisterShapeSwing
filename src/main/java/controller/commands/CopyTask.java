package controller.commands;

import controller.api.CopyBufferSvc;
import controller.api.SelectionSvc;

/* Responsible for updating the model's copy buffer with
 * the current selection on the canvas.
 */

public class CopyTask extends AbstractControllerTask
{
	@Override
	public void execute()
	{
		var selection = SelectionSvc.get();
		CopyBufferSvc.put(selection);
	}

}

