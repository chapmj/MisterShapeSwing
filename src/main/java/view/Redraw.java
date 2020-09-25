package view;

import java.util.ArrayList;

import controller.CanvasController;
import model.Dimensions;
import model.PointInt;
import model.Shape;
import model.ShapeCardinality;
import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeStyle;
import model.ShapeType;
import model.interfaces.ShapeComponent;
import view.interfaces.PaintCanvasBase;

// Check canvas state and redraw everything.
public class Redraw {
	private static PaintCanvasBase paintCanvas = CanvasController.getInstance().getPaintCanvas();
	
	public static void execute(ArrayList<ShapeComponent> shapeComponents) throws Exception {
		ShapeDrawer shapeDrawer = new ShapeDrawer();
		shapeDrawer.add(clearCanvas());
		shapeDrawer.add(shapeComponents);
		shapeDrawer.draw();
	}
	
	// Make a big white triangle the size of the canvas.
	static ShapeComponent clearCanvas() {
		return new Shape (
			ShapeType.RECTANGLE,
			new Dimensions(paintCanvas.getHeight(),paintCanvas.getWidth()),
			new ShapeStyle(
				ShapeColor.WHITE, 
				ShapeColor.WHITE, 
				ShapeShadingType.FILLED_IN),
			ShapeCardinality.NE,
			new PointInt(0,0));
	}
}
