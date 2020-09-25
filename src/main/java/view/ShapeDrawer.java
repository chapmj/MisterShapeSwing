package view;

import java.util.ArrayList;
import java.util.stream.Stream;

import controller.CanvasController;
import model.ShapeGroup;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

/* Draw shape data to canvas.
 * Shapes are appended to the shape drawer object prior to a call to draw them.
 * BUILDER PATTERN
 */
public class ShapeDrawer {
	private CanvasController canvasController;
	private ArrayList<IShape> shapeDrawerShapes = new ArrayList<>();
	private ArrayList<ShapeGroup> groups = new ArrayList<>();
	
	public ShapeDrawer (){
		canvasController = CanvasController.getInstance();
	}

	private DrawStrategy inferDrawStrategy(ShapeComponent component) {
		switch (component.getType()) {
			case RECTANGLE:	return new DrawStrategyRectangle(canvasController, component);
			case ELLIPSE:	return new DrawStrategyEllipse(canvasController, component);
			case TRIANGLE:	return new DrawStrategyTriangle(canvasController, component);
			case INVISIBLE_RECT: return new DrawStrategyInvisibleRect(component);
			default:		return new DrawStrategyRectangle(canvasController, component);
		}
	}

	// Divide ShapeComponents into IShapes and Shapegroups, then add all shapes to drawer.
	public void add(ArrayList<ShapeComponent> component) {
		// Add shapes to shapeDrawer pending
		component.stream()
			.filter(comp -> comp instanceof IShape)
			.map(s -> (IShape) s)
			.forEach(shapeDrawerShapes::add);
		
		// Store Groups
		component.stream()
			.filter(comp -> comp instanceof ShapeGroup)
			.map(g -> (ShapeGroup) g)
			.forEach(groups::add);
	
		// Store shapes and add to shapeDrawer pending
		component.stream()
			.filter(comp -> comp instanceof ShapeGroup)
			.map(g -> (ShapeGroup) g)
			.flatMap(c -> Stream.of(splitGroup(c)))
			.forEach(shapeDrawerShapes::addAll);
	}

	// Take a ShapeComponent and return all of the shapes in it.
	private ArrayList<IShape> splitGroup(ShapeComponent component) {
		ArrayList<IShape> shapes = new ArrayList<>();
		if (component instanceof ShapeGroup) {
			component.getShapes().stream()
				.map (shape -> (IShape) shape)
				.forEach(shapes::add);
		}
		return shapes;
	}

	// Builder execute
	public void draw() {
		shapeDrawerShapes.forEach((shape) -> inferDrawStrategy((ShapeComponent)shape).execute());
		groups.forEach((group) -> inferDrawStrategy((ShapeComponent)group).execute());
	}

	// Builder append
	public void add(ShapeComponent shape) {
		ArrayList<ShapeComponent> component = new ArrayList<>();
		component.add(shape);
		add(component);
	}
}