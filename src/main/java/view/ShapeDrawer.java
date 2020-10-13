package view;

import model.interfaces.IShape;
import model.shape.Shape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;
import view.api.CanvasSvc;
import view.drawstrategy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/* Draw shape data to canvas.
 * Shapes are appended to the shape drawer object prior to a call to draw them.
 * BUILDER PATTERN
 */
public class ShapeDrawer {
	private List<IShape> shapeDrawerShapes = new ArrayList<>();
	private List<ShapeGroup> groups = new ArrayList<>();
	
	public ShapeDrawer()
	{ }

	private DrawStrategy inferDrawStrategy(IShape component)
	{
	    var graphics = CanvasSvc.getGraphics();
		switch (component.getType())
		{
			case RECTANGLE:
				return new DrawStrategyRectangle(component, graphics);
			case ELLIPSE:
				return new DrawStrategyEllipse(component, graphics);
			case TRIANGLE:
				return new DrawStrategyTriangle(component, graphics);
			case INVISIBLE_RECT:
				return new DrawStrategyInvisibleRect(component, graphics);
			default:
				return new DrawStrategyNull(component);
		}
	}

	// Divide ShapeComponents into IShapes and Shapegroups, then add all shapes to drawer.
	public void add(List<IShape> component) {
		// Add shapes to shapeDrawer pending
		component.stream()
			.filter(Shape.class::isInstance)
			.forEach(shapeDrawerShapes::add);
		
		// Store Groups
		component.stream()
			.filter(ShapeGroup.class::isInstance)
			.map(comp -> (ShapeGroup) comp)
			.forEach(groups::add);
	
		// Store shapes and add to shapeDrawer pending
		component.stream()
			.filter(ShapeGroup.class::isInstance)
			.map(comp -> (ShapeGroup) comp)
			.flatMap(c -> Stream.of(splitGroup(c)))
			.forEach(shapeDrawerShapes::addAll);
	}

	// Take a ShapeComponent and return all of the shapes in it.
	private List<IShape> splitGroup(ShapeComponent component) {
		var shapes = new ArrayList<IShape>();

		if (component instanceof ShapeGroup) {
			shapes.addAll(component.getShapes());
		}

		return shapes;
	}

	// Builder execute
	public void draw()
	{
		shapeDrawerShapes.stream()
				//.map((shape) -> (ShapeComponent)shape)
				.map(this::inferDrawStrategy)
				.forEach(DrawStrategy::draw);

		groups.stream()
				.map(this::inferDrawStrategy)
				.forEach(DrawStrategy::draw);
	}

	// Builder append
	public void add(IShape shape) {
		var component = new ArrayList<IShape>();
		component.add(shape);
		add(component);
	}
}