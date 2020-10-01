package view;

import model.*;
import model.shape.ShapeComponent;
import model.shape.*;
import view.viewstate.ViewState;

import java.util.List;

// Check canvas state and redraw everything.
public class Redraw {

	public static void execute(List<ShapeComponent> shapeComponents) throws Exception {
		ShapeDrawer shapeDrawer = new ShapeDrawer();
		shapeDrawer.add(clearCanvas());
		shapeDrawer.add(shapeComponents);
		shapeDrawer.draw();
	}
	
	// Make a big white triangle the size of the canvas.
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
