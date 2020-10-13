package view.commands;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IShape;
import model.shape.*;
import view.ShapeDrawer;
import view.api.CanvasSvc;
import view.viewstate.ViewState;

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
		shapeDrawer.add(clearCanvas());
		shapeDrawer.add(shapeComponents);
		shapeDrawer.draw();
	}
	
	// Make a big white rectangle the size of the canvas.
	static ShapeComponent clearCanvas()
	{
		var canvasWidth = CanvasSvc.getCanvasWidth();
		var canvasHeight = CanvasSvc.getCanvasHeight();

		return new Shape (
			ShapeType.RECTANGLE,
			new Dimensions(canvasHeight, canvasWidth),
			new ShapeStyle(
				ShapeColor.WHITE,
				ShapeColor.WHITE,
				ShapeShadingType.FILLED_IN),
			ShapeCardinality.NE,
			new PointInt(0,0));
	}
}
