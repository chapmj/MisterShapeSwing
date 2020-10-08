package view.commands;

import model.*;
import model.shape.*;
import view.ShapeDrawer;
import view.viewstate.ViewState;

import java.util.List;
import java.util.function.Consumer;

// Check canvas state and redraw everything.
public class RedrawTask
{
	private final List<ShapeComponent> shapeComponents;

    public RedrawTask(List<ShapeComponent> shapeComponents)
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
		var canvas = ViewState.getCanvas();

		return new Shape (
			ShapeType.RECTANGLE,
			new Dimensions(canvas.getHeight(),canvas.getWidth()),
			new ShapeStyle(
				ShapeColor.WHITE,
				ShapeColor.WHITE,
				ShapeShadingType.FILLED_IN),
			ShapeCardinality.NE,
			new PointInt(0,0));
	}
}
