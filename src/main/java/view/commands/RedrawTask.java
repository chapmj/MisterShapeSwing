package view.commands;

import model.interfaces.IShape;
import view.api.CanvasSvc;
import view.drawstrategy.ShapeDrawer;

import java.util.List;

// Check canvas state and redraw everything.
public class RedrawTask
{
	private final List<IShape> shapeComponents;

    public RedrawTask(List<IShape> shapeComponents)
	{
		this.shapeComponents = shapeComponents;
	}

	public void execute() {
		ShapeDrawer shapeDrawer = new ShapeDrawer();
		shapeDrawer.add(CanvasSvc.clearCanvasShape());
		shapeDrawer.add(shapeComponents);
		shapeDrawer.draw();
	}
	
}
